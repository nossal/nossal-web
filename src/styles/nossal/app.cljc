(ns nossal.app
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reboot-simple :refer [reset]]))


(defstyles app
  reset
  [:body
    {:font-feature-settings "kern"
     :direction "ltr"
     :font-synthesis "none"
     :background "#efefef"
     :color "#333"
     :display "flex"
     :flex-direction "column"
     :min-height "100vh"}])
