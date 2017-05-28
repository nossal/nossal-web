(ns nossal.app
  (:require [nossal.web :refer [index dot log]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.java.io :as io]
            [compojure.handler :refer [site]])
  (:import [org.eclipse.jetty.server.handler.gzip GzipHandler]))


(defroutes app
  (GET "/" []
    (index))

  (GET "/p" request
    (str request))

  (GET "/dot" request
    (dot request))

  (GET "/weekly" request
    (log request))

  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(defn- add-gzip-handler [server]
  (.setHandler server
               (doto (GzipHandler.)
                 (.setIncludedMimeTypes (into-array ["text/css"
                                                     "text/plain"
                                                     "text/javascript"
                                                     "application/javascript"
                                                     "application/json"
                                                     "image/svg+xml"]))
                 (.setMinGzipSize 1024)
                 (.setHandler (.getHandler server)))))


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false :configurator add-gzip-handler})))
