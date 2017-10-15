(ns nossal.sw)

(def app-cache-name "nossal-app-cache")

(def files-to-cache ["/js/app.js"])

(defn- install-service-worker [e]
  (js/console.log "[ServiceWorker] Installing...")
  (-> js/caches
    (.open app-cache-name)
    (.then (fn [cache]
             (js/console.log "[ServiceWorker] Caching Shell")
             (.addAll cache (clj->js files-to-cache))))
    (.then (fn []
             (js/console.log "[ServiceWorker] Successfully Installed")))))

(defn- fetch-event [e]
  (js/console.log "[ServiceWorker] Fetch" (-> e .-request .-url))
  (js/fetch (.-request e)))

(defn- purge-old-caches [e]
  (js/console.log "[ServiceWorker] activate"))

(.addEventListener js/self "install" #(.waitUntil % (install-service-worker %)))
(.addEventListener js/self "fetch" #(.respondWith % (fetch-event %)))
(.addEventListener js/self "activate" #(.waitUntil % (purge-old-caches %)))
