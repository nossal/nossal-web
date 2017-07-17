(ns nossal.app
  (:require [nossal.web :refer [index dot log breakout]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.java.io :as io]
            [compojure.handler :refer [site]]))


(defroutes app
  (GET "/" request
    (index request))

  (GET "/p" request
    (str request))

  (GET "/dot" request
    (dot request))

  (GET "/weekly" request
    (log request))

  (GET "/breakout" request
    (breakout request))

  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
