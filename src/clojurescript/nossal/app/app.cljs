(ns nossal.app)

(defn is-service-worker-supported? []
  (exists? js/navigator.serviceWorker))

(defn register-service-worker [path-to-sw]
  (if (is-service-worker-supported?)
      (-> js/navigator
          .-serviceWorker
          (.register path-to-sw {:scope "/"})
          (.then (fn [reg] (js/console.log (str "Service Worker Registered for scope [" (.-scope reg) "]") reg))))))

(register-service-worker "sw.js")
