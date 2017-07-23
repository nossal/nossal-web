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

  [:section
   {:background "#fff"
    :padding (em 5)
    :text-align "center"
    :margin-top (em 5)}]
  [:strong {:font-weight "bold"}]
  [:h1
   {:font-size (em 3)
    :font-weight 500
    :line-height 1.04167
    :letter-spacing (em 0.006)
    :margin-top (em 0.7)}]
  [:p.call-to-action
    {:font-size (em 1.2)
     :margin-top (em 1.5)}]
  [:.call-button
    {:border "1px solid"
     :display "inline-block"
     :padding ".2em .4em"
     :color "#0070c9"
     :margin-top (em 1)
     :text-decoration "none"
     :font-size (em 3)}]
  [:p.link-description
    {:font-size (em 0.8)
      :margin-top (em 1.5)
      :color "#969696"}]

  [:.intro
    {:display "blok"
     :font-size (em 1.4)
     :max-width "60vw"
     :margin "auto"
     :font-weight 300
     :line-height 1.45455
     :letter-spacing (em 0.016)}
    [:p {:margin-top (em 1)}]
    [:.value {:color "#f32b63" :font-weight 600}]
    [:small
     {:display "block"
      :margin-top (em 1)
      :font-size (em 0.8)}]]
  [:footer
   {:margin-top (em 2)
    :color "#d527c7"
    :margin-bottom (em 3)
    :text-align "center"
    :font-size (em 0.7)}]

  (at-media {:max-width (px 736)}
    [:section {:padding (em 1) :padding-bottom (em 5) :margin-top (em 2)}]
    [:h1 {:font-size (em 2.3)}]
    [:footer {:margin-top (em 1)}]
    [:.call-button {:margin-top (em 0.5)}]))
