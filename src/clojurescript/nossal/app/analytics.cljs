(ns nossal.app.analytics
  (:require [nossal.data :refer [data-analytics]]
            [nossal.app.commons :refer [alert!]]))

(defn ->Array [array-like]
  (.call js/Array.prototype.slice array-like))

(defn gtag [& args]
  (do (alert! (clj->js args))
      (apply js/gtag (clj->js args))))

(defn addTrackEvent [where event]
  (.addEventListener where
                     (:on event)
                     (fn [e]
                       (gtag (:request event)
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
