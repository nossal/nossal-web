(defproject nossal "1.0.1-SNAPSHOT"
  :description "Nossal's personal web app"
  :url "https://noss.al"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.8.1"

  :dependencies [[org.clojure/clojure       "1.9.0"]
                 [org.clojure/data.json     "0.2.6"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.clojure/java.jdbc     "0.7.7"]
                 [org.clojure/core.cache    "0.7.1"]

                 [org.clojure/clojurescript "1.10.339"]
                 [bidi                      "2.1.3"]
                 [kibu/pushy                "0.3.8"]
                 [reagent                   "0.8.1"]
                 [re-frame                  "0.10.5"]
                 [garden                    "1.3.5"]
                 [hiccup                    "1.0.5"]

                 [compojure                 "1.6.1"]
                 [bidi                      "2.1.3"]
                 [ring/ring-jetty-adapter   "1.6.3"]
                 [ring/ring-defaults        "0.3.2"]
                 [ring/ring-json            "0.4.0"]

                 [image-resizer             "0.1.10"]
                 [environ                   "1.1.0"]
                 [clj-http                  "3.9.1"]

                 [org.postgresql/postgresql "42.2.4"]
                 [com.layerware/hugsql      "0.4.9"]
                 [heroku-database-url-to-jdbc "0.2.2"]]

  :jvm-opts ^:replace ["-Xmx1g" "-server"]

  :plugins [[lein-environ   "1.1.0"]
            [lein-cljsbuild "1.1.7" :exclusions [org.clojure/clojure]]
            [lein-figwheel  "0.5.16"]
            [lein-garden    "0.3.0"]
            [lein-ancient   "0.6.15"]
            [lein-ring      "0.12.4"]]

  :source-paths ["src/clojure", "src/clojurescript"]
  ; :prep-tasks [["garden" "once"]]
  :hooks [leiningen.cljsbuild]

  :uberjar-name "nossal.jar"

  :ring {:handler nossal.app/dev-app
         :auto-refresh? false
         :auto-reload? true
         :reload-paths ["src/clojure"]}

  :figwheel {:ring-handler nossal.app/dev-app
             :css-dirs ["resources/public/css"]}


  :clean-targets ^{:protect false} ["resources/public/js"
                                    "resources/public/css"
                                    :target-path]

  :garden {:builds [{:source-paths ["src/styles"]
                     :stylesheet nossal.styles/screen
                     :compiler {:output-to "resources/public/css/screen.css"
                                :vendors ["moz" "webkit"]
                                :pretty-print? false}}
                    {:source-paths ["src/styles"]
                     :stylesheet nossal.app/app
                     :compiler {:output-to "resources/public/css/app.css"
                                :vendors ["moz" "webkit"]
                                :pretty-print? false}}
                    {:source-paths ["src/styles"]
                     :stylesheet nossal.simple/simple
                     :compiler {:output-to "resources/public/css/simple.css"
                                :vendors ["moz" "webkit"]
                                :pretty-print? false}}]}

  :cljsbuild {:builds [{:id "app"
                        :source-paths ["src/clojurescript/nossal/app"]
                        :compiler {:output-to "resources/public/js/app.js"
                                   :output-dir "resources/public/js/app-out"
                                   :source-map "resources/public/js/app.js.map"
                                   :pretty-print false
                                   :parallel-build true
                                   :language-in :ecmascript5
                                   :optimizations :advanced}}
                       {:id "sw"
                        :source-paths ["src/clojurescript/nossal/sw"]
                        :compiler {:output-to "resources/public/js/sw.js"
                                   :output-dir "resources/public/js/sw-out"
                                   :source-map "resources/public/js/sw.js.map"
                                   :pretty-print false
                                   :parallel-build true
                                   :language-in :ecmascript5
                                   :optimizations :advanced}}]}

  :profiles {:production {:env {:dev false :production true}
                          :prep-tasks [["cljsbuild" "once" "app" "sw"] ["garden" "once"]]}

             :dev {:env {:dev "true", :production "false",
                         :database-url "postgres://nossal:nossal@localhost:5432/nossal"}
                   :prep-tasks [["garden" "once"]]
                   :cljsbuild
                     {:builds [{:id "dev-app"
                                :source-paths ["src/clojurescript/nossal/app"]
                                :figwheel true
                                :incremental true
                                :compiler {:output-to "resources/public/js/app.js"
                                           :output-dir "resources/public/js/app-dev-out"
                                           :main "nossal.app.app"
                                           :asset-path "/js/app-dev-out"
                                           :parallel-build true
                                           :pretty-print true
                                           :language-in :ecmascript5
                                           :optimizations :none}}]}}})
