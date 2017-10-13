(ns nossal.frontend)

(defn is-service-worker-supported?
  []
  (exists? js/navigator.serviceWorker))

(defn register-service-worker
  [path-to-sw]
  (if (is-service-worker-supported?)
      (-> js/navigator
          .-serviceWorker
          (.register path-to-sw)
          (.then #(js/console.log (str "Service Worker Registered[" path-to-sw ""))))))

(js/console.log "WOW! It works!!")
(register-service-worker "sw.js")
