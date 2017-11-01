(ns nossal.app)


(defn alert-me [what]
  (js/console.log what))

(defn online-status [e]
  (alert-me (if (.-navigator.onLine js/self) "ONLINE" "OFFLINE")))


(defn is-service-worker-supported? []
  (exists? js/navigator.serviceWorker))

(defn register-service-worker [path-to-sw scope]
  (if (is-service-worker-supported?)
    (-> js/navigator
        .-serviceWorker
        (.register path-to-sw {:scope scope})
        (.then (fn [reg] (js/console.log (str "Service Worker Registered for scope [" (.-scope reg) "]") reg))))
    (do
      (js/console.warn "%cShame on you for using a browser which doesn't support service workers." "background: #0986EE); color: #fefefe")
      (js/console.log "Download Firefox instead: https://www.mozilla.org/firefox/new/"))))

(register-service-worker "sw.js" ".")
(online-status)
(.addEventListener js/self "offline" #(online-status %))
(.addEventListener js/self "online" #(online-status %))
