(defproject nossal "1.0.1-SNAPSHOT"
  :description "Nossal's personal web app"
  :url "https://noss.al"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.9.0"

  :dependencies [[org.clojure/clojure       "1.10.1"]
                 [org.clojure/data.json     "1.0.0"]
                 [org.clojure/tools.logging "1.1.0"]
                 [org.clojure/core.cache    "1.0.207"]
                 [org.clojure/java.jdbc     "0.7.11"]

                 [org.clojure/clojurescript "1.10.773"]
                 [bidi                      "2.1.6"]
                 [kibu/pushy                "0.3.8"]
                 [reagent                   "0.10.0"]
                 [reagent-utils             "0.3.3"]
                 [re-frame                  "1.1.2"]
                 [garden                    "1.3.10"]
                 [hiccup                    "1.0.5"]

                 [compojure                 "1.6.2"]
                 [ring/ring-jetty-adapter   "1.8.2"]
                 [ring/ring-defaults        "0.3.2"]
                 [ring/ring-json            "0.5.0"]

                 [image-resizer             "0.1.10"]
                 [environ                   "1.2.0"]
                 [clj-http                  "3.11.0"]

                 [sitemap                   "0.4.0"]

                 [org.postgresql/postgresql      "42.2.18"]
                 [com.layerware/hugsql           "0.5.1"]
                 [heroku-database-url-to-jdbc    "0.2.2"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]

                 [ns-tracker                "0.4.0"]
                 [io.sentry/sentry-clj      "3.1.130"]]

  :plugins [[lein-environ   "1.2.0"]
            [lein-cljsbuild "1.1.8" :exclusions [org.clojure/clojure]]
            [lein-garden    "0.3.0"]
            [lein-ancient   "0.6.15"]
            [lein-ring      "0.12.5"]]

  :source-paths ["src/clojure", "src/clojurescript" "src/styles"]
  :prep-tasks [["garden" "once"]]

  :uberjar-name "nossal.jar"

  :ring {:handler nossal.app/dev-app
         :auto-refresh? false
         :auto-reload? true
         :reload-paths ["src/clojure"]}

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "resources/public/css"
                                    :target-path]

  :garden {:builds
           [{:source-paths ["src/styles"]
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

  :cljsbuild {:builds
              [{:id "app"
                :source-paths ["src/clojurescript/nossal/app"
                               "src/clojure/nossal/data"]
                :compiler {:output-to "resources/public/js/app.js"
                           :output-dir "resources/public/js/app-out"
                           :source-map "resources/public/js/app.js.map"
                           :pretty-print false
                                  ;  :parallel-build false
                           :language-out :ecmascript5
                           :optimizations :advanced}}
               {:id "sw"
                :source-paths ["src/clojurescript/nossal/sw"]
                :compiler {:output-to "resources/public/js/sw.js"
                           :output-dir "resources/public/js/sw-out"
                           :source-map "resources/public/js/sw.js.map"
                           :pretty-print false
                                  ;  :parallel-build false
                           :language-out :ecmascript5
                           :optimizations :advanced}}]}

  :aliases {"watch"   ["with-profile" "dev" "ring" "server-headless"]
            "release" ["with-profile" "production" "ring" "uberjar"]}

  :profiles
  {:production {:env {:dev "false"
                      :production "true"
                      :ga-tracking-id "UA-11532471-6"}
                :prep-tasks [["cljsbuild" "once"]]}

   :dev {:env {:dev "true"
               :production "false"
               :ga-tracking-id "UA-11532471-6"
               :database-url "postgres://nossal:nossal@mr-nas.local:5432/nossal"}
         :cljsbuild {:builds
                     [{:id "dev-app"
                       :source-paths ["src/clojurescript/nossal/app"
                                      "src/clojure/nossal/data"]
                       :incremental true
                       :compiler {:output-to "resources/public/js/app.js"
                                  :output-dir "resources/public/js/app-dev-out"
                                  :main "nossal.app.app"
                                  :asset-path "/js/app-dev-out"
                                  :parallel-build true
                                  :pretty-print true
                                  :language-out :ecmascript5
                                  :optimizations :none}}]}}})
