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

  [:#app {:background "#efece8"
          :height "100%",
          :border-radius (em 2)
          :overflow "hidden"
          :scrollbar-width "none"
          :overflow-y "scroll"}]
  [:#product-list {:display "flex"
                   :flex-flow "row wrap"}]
  [:.product-card {:background "white"
                   :flex-grow 1
                   :display "inline-block"
                   :color "#444"
                   :margin (em 1)
                   :padding (em 1)
                   :max-width (px 200)
                   :border-radius (em 1)}
   [:img {:width "100%"}]
   [:.price {:font-size (em 1.5)}
    [:.currency {:font-size (em 0.5)
                 :vertical-align "text-top"}]
    [:.cents {:font-size (em 0.5)
              :color "#aaa"}]]]
  [:.emoji [:svg {:width (px 32) :vertical-align "top"}]]
  [:.highlight {:background "linear-gradient(to right, #fce4ed, #ffe8cc)"
                :padding "2px 6px"
                :border-radius (px 2)
                :color "#505050"}]
  [:.colorfull-text {:background "linear-gradient(to right, #ff8a00, #da1b60)"
                     :background-clip "text"
                     :display "inline-block"
                     :text-fill-color "transparent"
                     :box-decoration-break "clone"}])
