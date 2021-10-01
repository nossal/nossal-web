(ns nossal.app.commons)

(defn alert! [what]
  ((println what)
   (.log js/console what)))

(defn online-status [e]
  (alert! (if (.-navigator.onLine js/self) "ONLINE!" "OFFLINE!")))

(defn is-service-worker-supported? []
  (exists? js/navigator.serviceWorker))

(defn register-service-worker [path-to-sw scope]
  (if (is-service-worker-supported?)
    (-> js/navigator
        .-serviceWorker
        (.register path-to-sw {:scope scope})
        (.then (fn [reg] (.log js/console (str "Service Worker Registered for scope [" (.-scope reg) "]") reg))))
    (do
      (.warn js/console "%cShame on you for using a browser which doesn't support service workers." "background: #0986EE); color: #fefefe")
      (.log js/console "Download Firefox instead: https://www.mozilla.org/firefox/new/"))))
