(ns nossal.sw
  (:require [cemerick.url :as url]))

(def app-cache-name "nossal-app-cache")

(def files-to-cache ["/js/app.js" "/"])

<<<<<<< Updated upstream
(defn- install-service-worker [e]
  (js/console.log "[ServiceWorker] Installing...")
=======
(defn parse-url [url]
  (into [:url :proto :host :port :rest] (doall (re-seq #"^(.*:)//([A-Za-z0-9\-\.]+)(:[0-9]+)?(.*)$" url))))

(defn- purge-old-cache [e]
  (-> js/caches
    .keys
    (.then (fn [keys]
            (->> keys
              (map #(when-not (contains? #{app-cache-name} %)
                      (.delete js/caches %)))
              clj->js
              js/Promise.all)))))

(defn- add-cache [request response]
>>>>>>> Stashed changes
  (-> js/caches
    (.open app-cache-name)
    (.then (fn [cache]
             (js/console.log "[ServiceWorker] Caching Shell")
             (.addAll cache (clj->js files-to-cache))))
    (.then (fn []
             (js/console.log "[ServiceWorker] Successfully Installed")))))

(defn- from-cache [request]
  (-> js/caches
    (.match request)
    (.then (fn [response]
<<<<<<< Updated upstream
             (or response (js/fetch request))))))
=======
              (or response (js/Promise.reject (str "no-match: " (.-url request))))))))

(defn- fetch [request]
  (-> (js/fetch request)
      (.then (fn [response]
              (if (.-ok response)
                (add-cache request (.clone response)))
              (js/console.log (.-url request) (clj->js (parse-url (.-url request))))
              response))
      (.catch (fn [] (from-cache request)))))

(defn- on-install [e]
  (js/console.log "[ServiceWorker] Installing...")
  (-> js/caches
    (.open app-cache-name)
    (.then (fn [cache]
              (.addAll cache (clj->js files-to-cache))))
    (.then (fn []
              (.skipWaiting js/self)
              (js/console.log "[ServiceWorker] Successfully Installed")))))
>>>>>>> Stashed changes

             ;(if (= "GET" (.-method e))
(defn- fetch-event [e]
  (let [request (.-request e)
        url (-> request .-url url/url)]
    (case (:host url)
      ("localhost" "noss.al" "nossal.com.br") (fetch request))))

<<<<<<< Updated upstream
(defn- purge-old-caches [e]
=======
(defn- on-activate [e]
  (.claim (.-clients js/self))
  (purge-old-cache e)
>>>>>>> Stashed changes
  (js/console.log "[ServiceWorker] activate"))

(.addEventListener js/self "install" #(.waitUntil % (install-service-worker %)))
(.addEventListener js/self "fetch" #(.respondWith % (fetch-event %)))
(.addEventListener js/self "activate" #(.waitUntil % (purge-old-caches %)))
