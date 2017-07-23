(ns nossal.simple
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reset :refer [reset]]))

(defstyles simple
  [reset]
  [:body
   {:font-family "\"SF Pro Display\",\"SF Pro Icons\",\"Helvetica Neue\",\"Helvetica\",\"Arial\",sans-serif"
    :font-size (px 16)
    :font-feature-settings "kern"
    :direction "ltr"
    :font-synthesis "none"
    :background "#efefef"
    :color "#333"
    :display "flex"
    :flex-direction "column"
    :min-height "100vh"}]

  [:.container {:background "#fff"}]
  [:h1
   {:font-size (em 3)
    :font-weight 500
    :line-height 1.04167
    :letter-spacing (em 0.006)}]
  [:.intro
    {:margin-top (em 0.7)
     :font-size (em 1.4)
     :font-weight 300
     :line-height 1.45455
     :letter-spacing (em 0.016)}])
