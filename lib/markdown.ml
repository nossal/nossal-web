(* Thin wrapper so the rest of the codebase never needs to know
   which Markdown library we're using underneath. *)

let to_html (md : string) : string =
  let doc = Cmarkit.Doc.of_string md in
  Cmarkit_html.of_doc ~safe:false doc
