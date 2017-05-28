(defproject nossal "1.0.1-SNAPSHOT"
  :description "Nossal's personal web app"
  :url "http://noss.al"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.2"]
                 [hiccup "1.0.5"]
                 [environ "1.0.3"]
                 [ring/ring-jetty-adapter "1.5.0"]

                 [org.clojure/clojurescript "1.9.456"]
                 [reagent "0.6.0"]
                 [garden "1.3.2"]
                 [clj-http "3.1.0"]]

  :plugins [[environ/environ.lein "0.3.1"]
            [lein-cljsbuild "1.1.4" :exclusions [org.clojure/clojure]]
            [lein-figwheel "0.5.9"]
            [lein-garden "0.3.0"]
            [lein-ring "0.11.0"]]

  :min-lein-version "2.7.0"

  :ring {:handler nossal.app/app :auto-refresh? true}
  :hooks [environ.leiningen.hooks]
  :uberjar-name "nossal.jar"
  ; :profiles {:production {:env {:production false}}}
  ; :prep-tasks [["garden" "once"]]

  :figwheel {:css-dirs ["resources/public/css"]}

  :source-paths ["src/clojure"]

  :cljsbuild {:builds {:dev {:source-paths ["src/clojurescript"]
                             :figwheel true
                             :compiler {:output-to "resources/public/js/app.js"
                                        :output-dir "resources/public/js/out"
                                        :optimizations :none
                                        :source-map true
                                        :incremental true
                                        :source-map-timestamp true}}
                       :main {:source-paths ["src/clojurescript"]
                              :compiler {:output-to "resources/public/js/app.js"
                                         :optimizations :advanced}}}}

  :garden {:builds [{:source-paths ["src/styles"]
                     :stylesheet nossal.core/screen
                     :compiler {:output-to "resources/public/css/screen.css"
                                :vendors ["moz" "webkit"]
                                :pretty-print? false}}]})
