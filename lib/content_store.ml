(* Holds the currently-live, rendered site content in memory.
   [replace] swaps the whole list in one step, so a request never
   sees a half-rebuilt state. *)

let posts : Post.t list ref = ref []

let all () : Post.t list = !posts

let by_slug (slug : string) : Post.t option =
  List.find_opt (fun (p : Post.t) -> p.slug = slug) !posts

let by_tag (tag : string) : Post.t list =
  List.filter (fun (p : Post.t) -> List.mem tag p.Post.tags) !posts

let replace (new_posts : Post.t list) : unit = posts := new_posts
