let read_file (path : string) : string =
  let ic = open_in_bin path in
  let n = in_channel_length ic in
  let s = really_input_string ic n in
  close_in ic;
  s

let load_post (dir : string) (filename : string) : Post.t option =
  let path = Filename.concat dir filename in
  let raw = read_file path in
  let fm_text, body = Front_matter.split raw in
  let meta = Front_matter.parse_meta fm_text in
  if meta.draft then None
  else
    let slug =
      match meta.slug_override with
      | Some s -> s
      | None -> Post.slug_of_filename filename
    in
    Some
      {
        Post.slug;
        title = meta.title;
        date = meta.date;
        tags = meta.tags;
        draft = meta.draft;
        html = Markdown.to_html body;
        filename;
      }

let is_markdown_file (name : string) : bool = Filename.check_suffix name ".md"

(* Scans [content_dir] for .md files, parses each one, drops drafts,
   and sorts newest-first by date string (works for ISO 8601 dates). *)
let build (content_dir : string) : Post.t list =
  let entries = try Sys.readdir content_dir with Sys_error _ -> [||] in
  entries |> Array.to_list |> List.filter is_markdown_file
  |> List.filter_map (load_post content_dir)
  |> List.sort (fun a b -> compare b.Post.date a.Post.date)
