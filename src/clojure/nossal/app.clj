(ns nossal.app
  (:require [nossal.web :refer [index dot log breakout coupom miner]]
            [nossal.util.web :refer [resize-image pwa-manifest]]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]
            [environ.core :refer [env]]
            [clojure.java.io :as io]
            [clojure.string :refer [ends-with?]]))


(defn ignore-trailing-slash [handler]
  (fn [request]
    (let [uri (:uri request)]
      (handler (assoc request :uri (if (and (not= "/" uri)
                                            (ends-with? uri "/"))
                                     (subs uri 0 (dec (count uri)))
                                     uri))))))

(defn site-defaults-options [site-defaults]
  (-> site-defaults
      (assoc-in [:security :ssl-redirect] true)
      (assoc-in [:security :frame-options] :sameorigin)
      (assoc :proxy true)))


(defn service-worker [mod]
  (response/resource-response (str "sw.js" mod) {:root "public/js"}))


(defroutes app-routes
  (GET "/" request
    (index request))

  (GET "/manifest.json" request
    (pwa-manifest))

  (GET "/sw.js" [] (service-worker ""))
  (GET "/sw.js.map" [] (service-worker ".map"))

  (GET "/p" request
    (str request (env :db_connection_name)))

  (GET "/dot" request
    (dot request))

  (GET "/log" request
    (log request))

  (GET "/breakout" request
    (breakout request))

  (GET "/miner" request
    (miner request))

  (GET "/_ah/health" request
    (str "👌"))

  (GET "/image/:name{[a-z_-]+}-:size{[0-9]+}.:ext" [name size ext]
    (resize-image name (int (read-string size)) ext))

  (GET "/cupons" []
    (response/redirect "/cupons/cabify"))
  (GET "/cupons/:service" [service :as request]
    (coupom service request))

  (GET "/%F0%9F%91%89:encoded-id{[a-zA-Z0-9]+}" [encoded-id] ; /👉:encoded-id
    (str "👉 " encoded-id))

  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(def app
  (-> app-routes
      (wrap-defaults (site-defaults-options site-defaults))
      (ignore-trailing-slash)))

(def dev-app
  (-> app-routes
      (wrap-defaults site-defaults)
      (ignore-trailing-slash)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 3000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
