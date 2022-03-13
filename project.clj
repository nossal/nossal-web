(defproject nossal "0.1.0-SNAPSHOT"
  :description "Example of full-stack Clojure/Script project"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.5.648"]
                 [thheller/shadow-cljs "2.17.8"]

                 ;; Logging
                 [com.taoensso/timbre "5.1.2"]
                 [com.fzakaria/slf4j-timbre "0.3.21"]

                 ;; Backend
                 [ring/ring-core "1.9.5"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [geheimtur "0.4.0"]
                 [hiccup "1.0.5"]
                 [garden "1.3.10"]
                 [mount "0.1.16"]
                 [wrench "0.3.3"]

                 ;; Frontend
                 [reagent "1.1.1"]
                 [re-frame "1.2.0"]
                 [day8.re-frame/http-fx "0.2.4"]
                 [bidi "2.1.6"]
                 [kibu/pushy "0.3.8"]]

  :plugins [[lein-ancient "0.7.0"]]
  :min-lein-version "2.9.0"

  :source-paths ["src/clj" "src/cljs" "src/cljc" "src/css"]
  :test-paths ["test/clj" "test/cljc"]
  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js" "node_modules"]

  :uberjar-name "nossal.jar"
  :main nossal.main

  :aliases {"js-watch" ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app"]
            "js-build" ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]}

  :profiles {:dev     {:repl-options {:init-ns user}
                       :source-paths ["dev"]
                       :dependencies [[org.clojure/tools.namespace "1.2.0"]
                                      [garden-gnome "0.1.0"]]}

             :uberjar {:aot          :all
                       :omit-source  true
                       :dependencies [[garden-gnome "0.1.0"]]
                       :prep-tasks   ["compile" "js-build" "css-build"]}})
