(lang dune 3.20)

(name nossal)
(source (github nossal/nossal-web))
(authors "Rodrigo Nossal <rodrigo@noss.al>")
(maintainers "Rodrigo Nossal <rodrigo@noss.al>")
(license LICENSE)
(generate_opam_files true)

(package
 (name nossal)
 (synopsis "A short synopsis")
 (description "A longer description")
 (depends
   (ocaml-lsp-server :build)
   (ocamlformat :build)
   (ocaml (>= 5.4))
   dream ))

