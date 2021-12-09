(ns nossal.app
  (:require [compojure.route :as route]
            [compojure.core :refer [defroutes context GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as response]

            [environ.core :refer [env]]
            [clojure.java.io :as io]
            [clojure.string :refer [ends-with?]]

            [sitemap.core :refer [generate-sitemap]]

            [nossal.web :refer [index dot log iframe-demo]]
            [nossal.util.web :refer [resize-image pwa-manifest]]
            [nossal.api.core :refer [debug]]
            [nossal.api.shortner :refer [create-database new-url redirect]]
            [nossal.coupons :refer [coupon coupon-index coupon-codes]]
            [sentry-clj.core :as sentry]))



(when-let [dsn (env :sentry-dsn)]
  (sentry/init! dsn))

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

(defn wrap-cache-control [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "cache-control"] "max-age=120, stale-while-revalidate=60, stale-if-error=3600"))))

(defn service-worker
  ([] (service-worker ""))
  ([mod]
   (response/content-type
    (response/resource-response (str "sw.js" mod) {:root "public/js"})
    "application/x-javascript; charset=utf-8")))


(defn gen-paths [paths]
  (map (fn [name]
         {:loc (str "https://noss.al/" name)
          :priority 1})
       paths))


(defroutes app-routes
  (GET "/" request
    (index request))

  (GET "/manifest.json" []
    (pwa-manifest))

  (GET "/sitemap.xml" []
    (response/content-type
     (response/response
      (generate-sitemap
       (concat
        (gen-paths (map #(str "cupons/" %) (keys coupon-codes)))
        (gen-paths ["" "cupons"]))))
     "text/xml"))

  (GET "/sw.js" [] (service-worker ""))
  (GET "/sw.js.map" [] (service-worker ".map"))

  (GET "/p" request
    (str request " DB:" (env :database-url)))

  (GET "/dot" request
    (dot request))

  (GET "/log" request
    (log request))

  ; (GET "/miner" request
  ;   (miner request))

  (GET "/_ah/health" request
    (str "👌"))

  (GET "/image/:name{[a-z_-]+}-:size{[0-9]+}.:ext" [name size ext]
    (resize-image name (int (read-string size)) ext))


  (GET "/cupons" request
    (coupon-index request))

  (GET "/cupons/:service" [service :as request]
    (coupon service request)) (POST "/debug" request
                                (debug request))

  (GET "/demo" request
    (iframe-demo request))

  (GET "/short/create-db" []
    (create-database))

  (POST "/short/new/:url" [url]
    (new-url url))

  (GET "/%F0%9F%91%89:encoded-id{[a-zA-Z0-9]+}" [encoded-id] ; /👉:encoded-id
    (redirect encoded-id))

  (GET "/%3E:encoded-id{[a-zA-Z0-9]+}" [encoded-id] ; />:encoded-id
    (redirect encoded-id))

  (route/resources "/")

  (GET "/:encoded-id{[a-zA-Z0-9]+}" [encoded-id] ; /:encoded-id
    (redirect encoded-id))

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(def app
  (-> app-routes
      (wrap-defaults (site-defaults-options site-defaults))
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-cache-control)
      (ignore-trailing-slash)))

(def dev-app-old
  (-> app-routes
      (wrap-json-body {:keywords? true :bigdecimals? true})
      ; (make-handler)))
     ; (wrap-defaults (site-defaults-options site-defaults) api-defaults)
      (ignore-trailing-slash)))

(def dev-app (wrap-reload (wrap-defaults #'app-routes site-defaults)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 3000))]
    (jetty/run-jetty #'app {:port port :join? false})))
