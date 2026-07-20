open Lwt.Infix

(* Runs `git -C <repo_path> <args>` without blocking the Lwt event loop,
   so a slow git operation never stalls requests to other visitors. *)
let run_git_command ~(repo_path : string) (args : string list) :
    (unit, string) result Lwt.t =
  let argv = Array.of_list ("git" :: "-C" :: repo_path :: args) in
  Lwt_process.exec ("git", argv) >>= fun status ->
  match status with
  | Unix.WEXITED 0 -> Lwt.return (Ok ())
  | Unix.WEXITED code ->
      Lwt.return
        (Error
           (Printf.sprintf "git %s failed (exit %d)" (String.concat " " args)
              code))
  | _ ->
      Lwt.return
        (Error
           (Printf.sprintf "git %s was interrupted" (String.concat " " args)))

let ensure_repo ~(repo_url : string) ~(repo_path : string) :
    (unit, string) result Lwt.t =
  if Sys.file_exists repo_path then Lwt.return (Ok ())
  else
    Lwt_process.exec ("git", [| "git"; "clone"; repo_url; repo_path |])
    >>= function
    | Unix.WEXITED 0 -> Lwt.return (Ok ())
    | _ -> Lwt.return (Error (Printf.sprintf "failed to clone %s" repo_url))

(* NOTE: assumes the default branch is "main" — change if yours differs. *)
let pull_and_rebuild ~(config : Config.t) : (int, string) result Lwt.t =
  ensure_repo ~repo_url:config.Config.repo_url
    ~repo_path:config.Config.repo_path
  >>= function
  | Error e -> Lwt.return (Error e)
  | Ok () -> (
      run_git_command ~repo_path:config.Config.repo_path [ "fetch"; "origin" ]
      >>= function
      | Error e -> Lwt.return (Error e)
      | Ok () -> (
          run_git_command ~repo_path:config.Config.repo_path
            [ "reset"; "--hard"; "origin/main" ]
          >>= function
          | Error e -> Lwt.return (Error e)
          | Ok () ->
              let posts = Loader.build config.Config.repo_path in
              Content_store.replace posts;
              Lwt.return (Ok (List.length posts))))
