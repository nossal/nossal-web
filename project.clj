(defproject nossal "1.0.1-SNAPSHOT"
  :description "Nossal's personal web app"
  :url "nossal.herokuapp.com"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [environ "1.0.3"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.9.36"]
                 [garden "1.3.2"]
                 [clj-http "3.1.0"]]

  :ring {:handler nossal.web/app :auto-refresh? true}
  :min-lein-version "2.0.0"

  :plugins [[environ/environ.lein "0.3.1"]
            [lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.4-3"]
            [lein-garden "0.2.8"]
            [lein-ring "0.9.7"]]

  :hooks [environ.leiningen.hooks]
  :uberjar-name "nossal.jar"
  :profiles {:production {:env {:production true}}}
  :prep-tasks [["garden" "once"]]
  :figwheel {:css-dirs [ "resources/public/css"]}

  :cljsbuild {:builds [{:id "nossal"}
                       :source-paths ["src/"]
                       :figwheel {:on-jsload "nossal.web/fig-reload"}
                       :compiler {:main "nossal.web"
                                  :output-to "resources/public/js/app.js"
                                  :output-dir "resources/public/js/out"
                                  :optimizations :none
                                  :source-map true
                                  :source-map-timestamp true
                                  :asset-path "js/out"}]}

  :garden {:builds [{;; Optional name of the build:
                     :id "screen1"
                     ;; Source paths where the stylesheet source code is
                     :source-paths ["src/"]
                     ;; The var containing your stylesheet:
                     :stylesheet styles.core/screen
                     ;; Compiler flags passed to `garden.core/css`:
                     :compiler {;; Where to save the file:
                                :output-to "resources/public/css/screen.css"
                                :vendors ["moz" "webkit"]
                                ;; Compress the output?
                                :pretty-print? true}}]})
