(ns nossal.app.app
  (:require [nossal.app.rev :as rev]
            [nossal.data :refer [data-analytics]]))

(enable-console-print!)

(defn alert! [what]
  (do (println what)
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

(register-service-worker "sw.js" ".")
(online-status nil)
(.addEventListener js/self "offline" #(online-status %))
(.addEventListener js/self "online" #(online-status %))

; (rev/mount-app)

; (defn on-jsload []
  ; (rev/mount-app))


(defn ->Array [array-like]
  (.call js/Array.prototype.slice array-like))

(defn gtag [& args]
  (do (alert! args)
      (apply js/gtag (clj->js args))))

(defn analytics-setup [data]
  (doseq [event (:triggers data)]
    (let [[ev conf] event]
      (condp = (:on conf)
        "click"
        (when-let [elements (not-empty (->Array (.querySelectorAll js/document (:selector conf))))]
          (doseq [element elements]
            (.addEventListener element (:on conf) (fn [e] (gtag (:request conf) (:on conf) (:vars conf))))))
        "visible" (alert! (str ">>>> " (:request conf)))
        (.addEventListener js/document (:on conf) (fn [e] (gtag (:request conf) (:on conf) (:vars conf))))))))
      ; (if (= "click" (:on conf))
      ;   (when-let [elements (not-empty (->Array (.querySelectorAll js/document (:selector conf))))]
      ;     (doseq [element elements]
      ;       (.addEventListener element (:on conf) (fn [e] (println (:vars conf))))))
      ;   (.addEventListener js/document (:on conf) (fn [e] (println (:request conf))))))))
        ; (let [element (or (.querySelectorAll js/document (:selector conf))) js/window])))))
      ;   (do
      ;    (println element)
      ;    (.addEventListener element (:on conf) (fn [e] (println e))))))))


; (.addEventListener js/document "DOMContentLoaded" (fn [] (analytics-setup data-analytics)))
(analytics-setup data-analytics)
