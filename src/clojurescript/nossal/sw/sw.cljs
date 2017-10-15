(ns nossal.sw)

(defn- install-service-worker [e]
  (js/console.log "[ServiceWorker] Installing..."))

(defn- fetch-event [e]
  (js/console.log "[ServiceWorker] Fetch" (-> e .-request .-url)))

(defn- purge-old-caches [e]
  (js/console.log "[ServiceWorker] activate"))

(.addEventListener js/self "install" #(.waitUntil % (install-service-worker %)))
(.addEventListener js/self "fetch" #(.respondWith % (fetch-event %)))
(.addEventListener js/self "activate" #(.waitUntil % (purge-old-caches %)))
