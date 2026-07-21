(lang dune 3.0)

(name nossal)
(source
 (github nossal/nossal-web))
(authors "Rodrigo Nossal <rodrigo@noss.al>")
(maintainers "Rodrigo Nossal <rodrigo@noss.al>")
(license LICENSE)
(generate_opam_files true)

(package
 (name nossal)
 (allow_empty)
 (synopsis "A short synopsis")
 (description "A longer description")
 (depends ocaml dream lwt cmarkit yaml digestif ))

