let () =
  let config = Blog_lib.Config.load () in
  (match Lwt_main.run (Blog_lib.Deploy.pull_and_rebuild ~config) with
  | Ok n -> Printf.printf "Initial build: %d posts loaded\n%!" n
  | Error e -> Printf.eprintf "Initial build failed: %s\n%!" e);
  Dream.run ~port:config.Blog_lib.Config.port
  @@ Dream.logger
  @@ Dream.router (Blog_lib.Router.routes config)
