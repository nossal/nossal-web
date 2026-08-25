type t = {
  slug : string;
  title : string;
  date : string;
  tags : string list;
  draft : bool;
  original_filename : Filename.type;
  output_filename : string;
}

let is_digit c = c >= '0' && c <= '9'

(* "2026-07-18-my-post-title.md" -> base "2026-07-18-my-post-title"
   -> strips the leading date prefix -> "my-post-title" *)
let strip_date_prefix (base : string) : string =
  let len = String.length base in
  if
    len > 11
    && is_digit base.[0]
    && is_digit base.[1]
    && is_digit base.[2]
    && is_digit base.[3]
    && base.[4] = '-'
    && is_digit base.[5]
    && is_digit base.[6]
    && base.[7] = '-'
    && is_digit base.[8]
    && is_digit base.[9]
    && base.[10] = '-'
  then String.sub base 11 (len - 11)
  else base

let slug_of_filename (filename : string) : string =
  Filename.remove_extension filename |> strip_date_prefix
