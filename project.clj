(defproject nossal "1.0.0-SNAPSHOT"
  :description "Nossal's personal web app"
  :url "nossal.herokuapp.com"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "0.0-3211"]
                 [lein-figwheel "0.3.1"]
                 [garden "1.2.5"]]

  :ring {:handler nossal.web/app :auto-refresh? true}
  :min-lein-version "2.0.0"

  :plugins [[environ/environ.lein "0.2.1"]
            [lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.3.1"]
            [lein-garden "0.2.5"]
            [lein-ring "0.9.3"]]

  :hooks [environ.leiningen.hooks]
  :uberjar-name "nossal.jar"
  :profiles {:production {:env {:production true}}}
  ;:prep-tasks [["garden" "once"]]

;  :cljsbuild {:builds [{:id "nossal"
;                        :source-paths ["src/"]
;                        :figwheel {:on-jsload "nossal.web/reload-hook"}
;                        :compiler {:main "nossal.web"
;                                   :asset-path "js/out"
;                                   :output-to "resources/public/js/app.js"
;                                   :output-dir "resources/public/js/out"
;                                   :source-map-timestamp true
;                                   :source-map true}}]}

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
