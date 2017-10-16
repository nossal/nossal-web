(ns nossal.sw
  (:require [cemerick.url :as url]))

(def app-cache-name "nossal-app-cache")

(def files-to-cache ["/js/app.js" "/"])

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
  (-> js/caches
    (.open app-cache-name)
    (.then (fn [cache]
             (.put cache (.-url request) response)))))

(defn- fetch [request]
  (-> js/caches
    (.match request)
    (.then (fn [response]
             (if response
               response
               (-> (js/fetch request)
                   (.then (fn [resp]
                            (add-cache request (.clone resp))
                            resp))))))))


(defn- on-install [e]
  (js/console.log "[ServiceWorker] Installing...")
  (-> js/caches
    (.open app-cache-name)
    (.then (fn [cache]
              (.addAll cache (clj->js files-to-cache))))
    (.then (fn []
              (js/console.log "[ServiceWorker] Successfully Installed")))))

(defn- on-fetch [e]
  (let [request (.-request e)
        url (-> request .-url url/url)]
    (case (:host url)
      ("localhost" "noss.al" "nossal.com.br") (fetch request)
      (js/fetch request))))

(defn- on-activate [e]
  (purge-old-cache e)
  (js/console.log "[ServiceWorker] activate"))


(.addEventListener js/self "install" #(.waitUntil % (on-install %)))
(.addEventListener js/self "fetch" #(.respondWith % (on-fetch %)))
(.addEventListener js/self "activate" #(.waitUntil % (on-activate %)))
