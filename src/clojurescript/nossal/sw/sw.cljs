(ns nossal.sw
  (:require [cemerick.url :as url]))

(def app-cache-name "nossal-app-cache")

(def files-to-cache ["/js/app.js" "/"])

(defn- install-service-worker [e]
  (js/console.log "[ServiceWorker] Installing...")
  (-> js/caches
    (.open app-cache-name)
    (.then (fn [cache]
             (js/console.log "[ServiceWorker] Caching Shell")
             (.addAll cache (clj->js files-to-cache))))
    (.then (fn []
             (js/console.log "[ServiceWorker] Successfully Installed")))))

(defn- fetch [request]
  (-> js/caches
    (.match request)
    (.then (fn [response]
             (or response (js/fetch request))))))

             ;(if (= "GET" (.-method e))
(defn- fetch-event [e]
  (let [request (.-request e)
        url (-> request .-url url/url)]
    (case (:host url)
      ("localhost" "noss.al" "nossal.com.br") (fetch request))))

(defn- purge-old-caches [e]
  (js/console.log "[ServiceWorker] activate"))

(.addEventListener js/self "install" #(.waitUntil % (install-service-worker %)))
(.addEventListener js/self "fetch" #(.respondWith % (fetch-event %)))
(.addEventListener js/self "activate" #(.waitUntil % (purge-old-caches %)))
