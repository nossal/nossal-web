(ns nossal.shop
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reboot-simple :refer [reset]]
            [nossal.common :refer :all]))

(defstyles shop
  reset
  egg
  footer
  defaults
  [:html {:height "100%" :overflow "hidden"}]
  [:body {:height "100%"
          :overflow "hidden"
          :background "#000"
          :margin 0}]
  [:.alert {:color "orange"}]
  [:#app-container {:background "#f1f1f1"
                    :height "100%"
                    :border-radius (em 2)
                    :overflow "hidden"
                    :scrollbar-width "none"
                    :overflow-y "scroll"}]
  [:.product-card {:background "white"
                   :color "#444"
                   :margin (em 1)
                   :padding (em 1)
                   :max-width (px 200)
                   :border-radius (em 1)}
    [:img {:width "100%"}]
    [:.price {:font-size (em 1.5)}]])




