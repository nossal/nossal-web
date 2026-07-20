(* All configuration comes from environment variables so nothing
   sensitive (the webhook secret) ever lives in the repo or in code. *)

type t = {
  port : int;
  webhook_secret : string;
  repo_path : string;   (* local clone of the content repo, on disk *)
  repo_url : string;    (* remote git URL, e.g. git@github.com:you/blog-content.git *)
  site_title : string;
}

let getenv_default name default =
  match Sys.getenv_opt name with
  | Some v -> v
  | None -> default

let getenv_required name =
  match Sys.getenv_opt name with
  | Some v -> v
  | None -> failwith (Printf.sprintf "Required environment variable %s is not set" name)

let load () : t =
  {
    port = int_of_string (getenv_default "BLOG_PORT" "8080");
    webhook_secret = getenv_required "BLOG_WEBHOOK_SECRET";
    repo_path = getenv_default "BLOG_REPO_PATH" "./content";
    repo_url = getenv_required "BLOG_REPO_URL";
    site_title = getenv_default "BLOG_SITE_TITLE" "My Blog";
  }
