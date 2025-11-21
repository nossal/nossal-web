let () =
  Dream.run
    ~interface:"0.0.0.0"
    ~port:
      (match Sys.getenv_opt "PORT" with
       | Some port -> int_of_string port
       | None -> 3080)
  @@ Dream.logger
  @@ Dream.router
       [ Dream.get "/healthz" (fun _ ->
            Dream.respond ~headers:[("Content-Type", "text/plain")] "Alive!")
       ; Dream.get "/static/**" @@ Dream.static "resources/public"
       ; Dream.get "/favicon.ico" (Dream.from_filesystem "resources/public" "favicon.ico")
       ; Dream.get "/" (fun _ -> Dream.html Home.render)
       ]
;;
