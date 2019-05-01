(ns nossal.app
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as u]
            [garden.color :as c]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reboot-simple :refer [reset]]
            [nossal.common :refer :all]))

(defstyles app
  reset
  body
  egg
  footer
  [:.alert
   {:display "inline-block"
    :border-image "linear-gradient(to bottom, black, rgba(0, 0, 0, 0)) 1 100%;"
    :border-width "5px"
    :border-style "solid"
    :background "#fff"
    :color "#333"
    :padding "3px 15px 4px"
    :border-radius (u/em 1)}
   [:&:before
    {:content "\"\""
     :background "#ddd"}]])
