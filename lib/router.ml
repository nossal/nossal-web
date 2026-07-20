open Lwt.Infix

let index_handler (config : Config.t) (_request : Dream.request) :
    Dream.response Lwt.t =
  let posts = Content_store.all () in
  Dream.html (Templates.index ~site_title:config.Config.site_title posts)

let post_handler (config : Config.t) (request : Dream.request) :
    Dream.response Lwt.t =
  let slug = Dream.param request "slug" in
  match Content_store.by_slug slug with
  | Some p -> Dream.html (Templates.post_page ~site_title:config.Config.site_title p)
  | None ->
    Dream.html ~status:`Not_Found (Templates.not_found ~site_title:config.Config.site_title)

let webhook_handler (config : Config.t) (request : Dream.request) :
    Dream.response Lwt.t =
  Dream.body request >>= fun body ->
  let signature_header = Dream.header request "X-Hub-Signature-256" in
  if not (Webhook.verify ~secret:config.Config.webhook_secret ~body ~signature_header)
  then Dream.respond ~status:`Unauthorized "invalid signature"
  else
    Deploy.pull_and_rebuild ~config >>= function
    | Ok n -> Dream.respond (Printf.sprintf "rebuilt %d posts" n)
    | Error e -> Dream.respond ~status:`Internal_Server_Error e

let routes (config : Config.t) : Dream.route list =
  [
    Dream.get "/" (index_handler config);
    Dream.get "/posts/:slug" (post_handler config);
    Dream.post "/webhook/deploy" (webhook_handler config);
    Dream.get "/static/**" (Dream.static "static");
  ]
