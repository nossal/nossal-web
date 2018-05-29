(defproject nossal "1.0.1-SNAPSHOT"
  :description "Nossal's personal web app"
  :url "https://noss.al"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.8.1"

  :dependencies [[org.clojure/clojure       "1.9.0"]
                 [compojure                 "1.6.1"]
                 [hiccup                    "1.0.5"]
                 [image-resizer             "0.1.10"]
                 [environ                   "1.1.0"]
                 [ring/ring-jetty-adapter   "1.6.3"]
                 [ring/ring-defaults        "0.3.1"]
                 [org.clojure/clojurescript "1.10.238"]
                 [garden                    "1.3.5"]
                 [clj-http                  "3.9.0"]
                 [org.clojure/data.json     "0.2.6"]
                 [ring/ring-json            "0.4.0"]
                 [org.clojure/java.jdbc     "0.7.6"]
                 [org.postgresql/postgresql "42.2.2"]
                 [com.layerware/hugsql      "0.4.9"]]

  :plugins [[lein-environ   "1.1.0"]
            [lein-cljsbuild "1.1.7" :exclusions [org.clojure/clojure]]
            [lein-figwheel  "0.5.16"]
            [lein-garden    "0.3.0"]
            [lein-ancient   "0.6.15"]
            [lein-ring      "0.12.4"]]

  :source-paths ["src/clojure", "src/styles"]

  :hooks [leiningen.cljsbuild]
  :uberjar-name "nossal.jar"

  :ring {:handler nossal.app/dev-app}

  :figwheel {:ring-handler nossal.app/dev-app
             :css-dirs ["resources/public/css"]}


  :clean-targets ^{:protect false} ["resources/public/js"
                                    "resources/public/css"
                                    :target-path]

  :cljsbuild {:builds {:app {:source-paths ["src/clojurescript/nossal/app"]
                              :compiler {:output-to "resources/public/js/app.js"
                                         :pretty-print false
                                         :parallel-build true
                                         :language-in :ecmascript5
                                         :optimizations :advanced}}
                       :sw {:source-paths ["src/clojurescript/nossal/sw"]
                             :compiler {:output-to "resources/public/js/sw.js"
                                        :pretty-print false
                                        :language-in :ecmascript5
                                        :parallel-build true
                                        :optimizations :advanced}}}}


  :garden {:builds [{:source-paths ["src/styles"]
                     :stylesheet nossal.styles/screen
                     :compiler {:output-to "resources/public/css/screen.css"
                                :vendors ["moz" "webkit"]
                                :pretty-print? false}}
                    {:source-paths ["src/styles"]
                     :stylesheet nossal.simple/simple
                     :compiler {:output-to "resources/public/css/simple.css"
                                :vendors ["moz" "webkit"]
                                :pretty-print? false}}]}
                    ; {:source-paths ["src/styles"]
                    ;  :stylesheet nossal.reboot/reset
                    ;  :compiler {:output-to "resources/public/css/reboot.css"
                    ;             :vendors ["moz" "webkit" "ms"]
                    ;             :pretty-print? true}}]}

  :profiles {:production {:env {:dev false :production true}
                          :prep-tasks [["cljsbuild" "once" "app" "sw"] ["garden" "once"]]}

             :dev {:env {:dev "true", :production "false", :database-url "postgres://localhost/nossal"}
                  ;  :prep-tasks [["garden" "once"]]
                   :cljsbuild {:builds
                               {:app {:source-paths ["src/clojurescript/nossal/app"]
                                      :figwheel true
                                      :incremental true
                                      :compiler {:output-to "resources/public/js/app.js"
                                                 :output-dir "resources/public/js/app-out"
                                                 :main "nossal.app"
                                                 :asset-path "js/app-out"
                                                 :parallel-build true
                                                 :pretty-print true
                                                 :language-in :ecmascript5
                                                 :optimizations :none}}
                                :sw {:source-paths ["src/clojurescript/nossal/sw"]
                                    ;  :figwheel true
                                     :incremental true
                                     :compiler {:output-to "resources/public/js/sw.js"
                                                :output-dir "resources/public/js/sw-out"
                                                :source-map "resources/public/js/sw.js.map"
                                                :asset-path "js/sw-out"
                                                :language-in :ecmascript5
                                                :parallel-build true
                                                :optimizations :advanced}}}}}})
