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
  (do (alert! (clj->js args))
      (apply js/gtag (clj->js args))))


(defn addTrackEvent [where event]
  (.addEventListener where
                     (:on event)
                     (fn [e] (gtag (:request event)
                                   (:eventAction (:vars event))
                                   (let [ev (:vars event)]
                                     {:event_category (:eventCategory ev)
                                      :event_label (:eventLabel ev)
                                      :value (:value ev)})))))

(defn analytics-setup [data]
  (doseq [event (:triggers data)]
    (let [[ev conf] event]
      (condp = (:on conf)
        "click"
        (when-let [elements (not-empty (->Array (.querySelectorAll js/document (:selector conf))))]
          (doseq [element elements]
            (addTrackEvent element conf)))
        "visible" (alert! (str ">>>> " (:request conf)))
        (addTrackEvent js/document conf)))))

; (.addEventListener js/document "DOMContentLoaded" (fn [] (analytics-setup data-analytics)))
(analytics-setup data-analytics)
