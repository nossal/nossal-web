(ns nossal.app
  (:require [nossal.web :refer [index dot log breakout coupom miner]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes routes wrap-routes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [ring.middleware.defaults :refer :all]
            [environ.core :refer [env]]
            [clojure.java.io :as io]))

(defroutes app-routes
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


(assoc secure-site-defaults :proxy true)

(def app
  (routes (-> app-routes (wrap-routes wrap-defaults secure-site-defaults))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 3000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
