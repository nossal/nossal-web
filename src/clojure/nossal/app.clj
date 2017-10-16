(ns nossal.app
  (:require [nossal.web :refer [index dot log breakout coupom miner]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [environ.core :refer [env]]
            [clojure.java.io :as io]))


(defn service-worker [mod]
  (response/resource-response (str "sw.js" mod) {:root "public/js"}))

(defroutes app-routes
  (GET "/" request
    (index request))

  (GET "/sw.js" [] (service-worker ""))
  (GET "/sw.js.map" [] (service-worker ".map"))

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

  (GET "/cupons" []
    (response/redirect "/cupons/uber"))
  (GET "/cupons/:service" [service :as request]
    (coupom service request))


  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(def app (wrap-defaults #'app-routes site-defaults))


(def dev-app (wrap-reload (wrap-defaults #'app-routes site-defaults)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 3000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
