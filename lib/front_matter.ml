(* A post file looks like:

   ---
   title: My Post
   date: 2026-07-18
   tags: [ocaml, blogging]
   draft: false
   ---
   The rest is Markdown body text.

   [split] separates the two parts; [parse_meta] turns the YAML part
   into a structured record. *)

let split (raw : string) : string * string =
  let lines = String.split_on_char '\n' raw in
  match lines with
  | first :: rest when String.trim first = "---" ->
      let rec take_until_delim acc = function
        | [] -> (List.rev acc, [])
        | line :: tl when String.trim line = "---" -> (List.rev acc, tl)
        | line :: tl -> take_until_delim (line :: acc) tl
      in
      let fm_lines, body_lines = take_until_delim [] rest in
      (String.concat "\n" fm_lines, String.concat "\n" body_lines)
  | _ -> ("", raw)

type meta = {
  title : string;
  date : string;
  tags : string list;
  draft : bool;
  slug_override : string option;
}

let empty_meta =
  { title = ""; date = ""; tags = []; draft = false; slug_override = None }

let parse_meta (yaml_text : string) : meta =
  if String.trim yaml_text = "" then empty_meta
  else
    match Yaml.of_string yaml_text with
    | Error (`Msg msg) -> failwith ("Front matter YAML error: " ^ msg)
    | Ok (`O fields) ->
        let find key = List.assoc_opt key fields in
        let as_string = function Some (`String s) -> s | _ -> "" in
        let as_bool = function Some (`Bool b) -> b | _ -> false in
        let as_string_opt = function Some (`String s) -> Some s | _ -> None in
        let as_string_list = function
          | Some (`A items) ->
              List.filter_map (function `String s -> Some s | _ -> None) items
          | _ -> []
        in
        {
          title = as_string (find "title");
          date = as_string (find "date");
          tags = as_string_list (find "tags");
          draft = as_bool (find "draft");
          slug_override = as_string_opt (find "slug");
        }
    | Ok _ -> empty_meta
