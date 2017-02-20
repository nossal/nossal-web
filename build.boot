(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.8.0"]
                  [compojure "1.5.2"]
                  [environ "1.0.3"]
                  [hiccup "1.0.5"]
                  [ring-server "0.4.0"]

                  [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                  [adzerk/boot-cljs-repl "0.3.3" :scope "test"]
                  [adzerk/boot-reload "0.5.1" :scope "test"]
                  [pandeiro/boot-http "0.7.6" :scope "test"]

                  [org.clojure/clojurescript "1.9.456"]
                  [garden "1.3.2"]
                  [reagent "0.6.0"]])

(task-options!
  pom {:project 'nossal
       :version "1.1.0-SNAPSHOT"}
  jar {:manifest {"Foo" "bar"}}
  repl {:init-ns 'nossal.app})


(deftask build
  "Build my project."
  []
  (comp (pom) (jar) (install)))
