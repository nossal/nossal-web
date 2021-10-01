(ns nossal.app.core
  (:require [nossal.app.commons :refer [online-status register-service-worker]]
            [nossal.app.analytics :refer [analytics-setup]]
            [nossal.data :refer [data-analytics]]))

(register-service-worker "sw.js" ".")

(online-status nil)
(.addEventListener js/self "offline" #(online-status %))
(.addEventListener js/self "online" #(online-status %))

(analytics-setup data-analytics)
