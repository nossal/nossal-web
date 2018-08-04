(ns nossal.app
  (:require [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults api-defaults]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]

            [environ.core :refer [env]]
            [clojure.java.io :as io]
            [clojure.string :refer [ends-with?]]

            [nossal.web :refer [index dot log breakout miner iframe-demo]]
            [nossal.util.web :refer [resize-image pwa-manifest]]
            [nossal.api.core :refer [debug]]
            [nossal.api.shortner :refer [create-database new-url redirect]]
            [nossal.coupons :refer [coupom]]))


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
  (response/content-type
    (response/resource-response (str "sw.js" mod) {:root "public/js"})
    "text/javascript"))


(defroutes app-routes
  (GET "/" request
    (index request))

  (GET "/manifest.json" request
    (pwa-manifest))

  (GET "/sw.js" [] (service-worker ""))
  (GET "/sw.js.map" [] (service-worker ".map"))

  (GET "/p" request
    (str request " DB:" (env :database-url)))

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


  (POST "/debug" request
    (debug request))

  (GET "/demo" request
    (iframe-demo request))


  (POST "/short/create-db" []
    (create-database))

  (POST "/short/new/:url" [url]
    (new-url url))

  (GET "/%F0%9F%91%89:encoded-id{[a-zA-Z0-9]+}" [encoded-id] ; /👉:encoded-id
    (redirect encoded-id))


  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(def app
  (-> app-routes
      ; (wrap-defaults (site-defaults-options site-defaults) )
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (ignore-trailing-slash)))

(def dev-app
  (-> app-routes
      ; (wrap-defaults (site-defaults-options site-defaults) api-defaults)
      (wrap-json-body {:keywords? true :bigdecimals? true})))
      ; (ignore-trailing-slash)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 3000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
