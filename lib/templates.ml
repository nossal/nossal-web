let escape (s : string) : string =
  let buf = Buffer.create (String.length s) in
  String.iter
    (fun c ->
      match c with
      | '&' -> Buffer.add_string buf "&amp;"
      | '<' -> Buffer.add_string buf "&lt;"
      | '>' -> Buffer.add_string buf "&gt;"
      | '"' -> Buffer.add_string buf "&quot;"
      | c -> Buffer.add_char buf c)
    s;
  Buffer.contents buf

let layout ~(site_title : string) ~(page_title : string) ~(body : string) : string
    =
  Printf.sprintf
    {|<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>%s - %s</title>
  <link rel="stylesheet" href="/static/style.css">
</head>
<body>
  <header><h1><a href="/">%s</a></h1></header>
  <main>%s</main>
</body>
</html>|}
    (escape page_title) (escape site_title) (escape site_title) body

let post_list_item (p : Post.t) : string =
  Printf.sprintf
    {|<article class="post-summary">
  <h2><a href="/posts/%s">%s</a></h2>
  <p class="meta">%s &middot; %s</p>
</article>|}
    (escape p.Post.slug) (escape p.Post.title) (escape p.Post.date)
    (String.concat ", " (List.map escape p.Post.tags))

let index ~(site_title : string) (posts : Post.t list) : string =
  let items = posts |> List.map post_list_item |> String.concat "\n" in
  layout ~site_title ~page_title:"Home" ~body:items

let post_page ~(site_title : string) (p : Post.t) : string =
  let body =
    Printf.sprintf
      {|<article class="post">
  <h1>%s</h1>
  <p class="meta">%s &middot; %s</p>
  <div class="content">%s</div>
</article>|}
      (escape p.Post.title) (escape p.Post.date)
      (String.concat ", " (List.map escape p.Post.tags))
      p.Post.html
  in
  layout ~site_title ~page_title:p.Post.title ~body

let not_found ~(site_title : string) : string =
  layout ~site_title ~page_title:"Not Found" ~body:"<p>Page not found.</p>"
