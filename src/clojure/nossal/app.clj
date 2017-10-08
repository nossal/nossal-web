(ns nossal.app
  (:require [nossal.web :refer [index dot log breakout coupom miner]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [environ.core :refer [env]]
            [clojure.java.io :as io]))


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

  (GET "/miner" request
    (miner request))

  (GET "/_ah/health" request
    (str "👌"))

  (GET "/cupons/:service" [service :as request]
    (coupom service request))
  (GET "/cupons" []
    (response/redirect "/cupons/uber"))


  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
