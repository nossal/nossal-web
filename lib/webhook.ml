(* GitHub (and GitLab, with a different header name) sign webhook
   payloads with HMAC-SHA256 over the raw request body, sent as a
   header like "sha256=<hex digest>". Verifying this signature is
   the entire authentication mechanism here — no password, no
   session, just proof the request came from the git host holding
   the shared secret. *)

let hex_of_string (s : string) : string =
  let buf = Buffer.create (String.length s * 2) in
  String.iter
    (fun c -> Buffer.add_string buf (Printf.sprintf "%02x" (Char.code c)))
    s;
  Buffer.contents buf

(* Constant-time comparison avoids leaking information via timing. *)
let constant_time_eq (a : string) (b : string) : bool =
  if String.length a <> String.length b then false
  else begin
    let diff = ref 0 in
    String.iteri (fun i c -> diff := !diff lor (Char.code c lxor Char.code b.[i])) a;
    !diff = 0
  end

let verify ~(secret : string) ~(body : string)
    ~(signature_header : string option) : bool =
  match signature_header with
  | None -> false
  | Some header ->
    let prefix = "sha256=" in
    let prefix_len = String.length prefix in
    if String.length header <= prefix_len || String.sub header 0 prefix_len <> prefix
    then false
    else
      let given_hex =
        String.sub header prefix_len (String.length header - prefix_len)
        |> String.lowercase_ascii
      in
      let computed =
        Digestif.SHA256.hmac_string ~key:secret body
        |> Digestif.SHA256.to_raw_string |> hex_of_string
      in
      constant_time_eq given_hex computed
