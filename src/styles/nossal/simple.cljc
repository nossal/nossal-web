(ns nossal.simple
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reboot-simple :refer [reset]]))

(defstyles simple
  [reset]
  [:body
   {:font-feature-settings "kern"
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
    :padding-top (em 2)
    :text-align "center"
    :margin-top (em 1)}]
  [:section.others
   [:a
    {:color "#0070c9"
     :display "inline-block"
     :border "1px solid #c292c9"
     :text-decoration "none"
     :border-radius "1em"
     :padding "0.3em 0.6em"}
    [:&:after {:content "'🚀'" :padding-left (em 0.3)}]]]

  [:sup {:font-size (em 0.6)
         :color "#a2a2a2"
         :background "#fdfcfc";
         :box-shadow "0 0 1em rgba(0,0,0,0.1)"
         :border-radius (em 3)
         :padding ".15em .3em"
         :margin-left (em 0.5)}]
  [:ruby {:font-size (em 0.8)}]
  [:.column {:margin 0 :padding 0 :display :block}
   [:li {:list-style-type :none}]]
  [:.grid
   {:display :inline-flex
    :flex-wrap :wrap
    :gap (em 1)
    :justify-content :center}]
  [:li
   {:padding "0.7em 2em"
    :z-index 1
    :background "transparent"
    :margin-bottom (em 1)
    :position "relative"}
   [:a
    {:font-size (em 1.6)}
    [:&:hover {:text-decoration :none}]
    [:&:after
     {:position "absolute"
      :top 0
      :right 0
      :bottom 0
      :left 0
      :z-index 1
      :pointer-events "auto"
      :content "''"
      :background-color "rgba(0,0,0,0)"}]]
   [:&:before
    {:content "' '"
     :position "absolute"
     :display "block"
     :width "calc(100% - 4px)"
     :height "calc(100% - 4px)"
     :border-radius (em 0.2)
     :top (px 2)
     :left (px 2)
     :z-index -1
     :background "#f9f9f9"}]
   [:&:after
    {:content "' '"
     :display "block"
     :border-radius (em 0.3)
     :position "absolute"
     :width (percent 100)
     :height (percent 100)
     :top 0
     :left 0
     :z-index -3
     :transition "opacity .3s ease-in-out"
     :background "linear-gradient(269.16deg,#ffe580 -15.83%,#ff7571 -4.97%,#ff7270 15.69%,#ea5dad 32.43%,#c2a0fd 50.09%,#9867f0 67.47%,#3bf0e4 84.13%,#ce33a7 105.13%,#b2f4b6 123.24%)"}]]

  [:strong {:font-weight "bold"}]
  [:h1
   {:font-size (em 3)
    :font-weight 500
    :line-height 1.04167
    :letter-spacing (em 0.006)
    :margin-top (em 0.7)
    :margin-bottom (em 1)}]
  [:p.call-to-action
   {:font-size (em 1.2)
    :margin-top (em 1.5)}]
  [:#coupon-code
   {:border 0
    :height (em 6)
    :text-align "center"}]
  [:.get-coupon
   {:border :none
    :background "#f7f7f7"
    :max-width (px 360)
    :padding ".2em .4em"
    :color "#454246"
    :text-decoration "none"
    :font-size (em 3)}]
  [:p.link-description
   {:font-size (em 0.8)
    :margin-top (em 1.5)
    :color "#969696"}]

  [:.intro
   {:display "block"
    :font-size (em 1.4)
    :max-width "80vw"
    :margin "auto"
    :margin-bottom (em 1)
    :font-weight 300
    :line-height 1.45455
    :letter-spacing (em 0.016)}

   [:p.warn
    {:color "#ff2400"
     :padding (em 0.5)
     :background "#fffde1"
     :font-size (em 0.8)
     :line-height (em 1.3)}
    [:small {:margin-top 0}]
    [:span {:font-weight "bold"
            :color "#f00"
            :text-transform "uppercase"}]]

   [:p {:margin-top (em 1)}]
   [:.value {:color "#f32b63" :font-weight 600}]
   [:small
    {:display "block"
     :margin-top (em 1)
     :font-size (em 0.8)}]]
  [:footer
   {:margin-top (em 2)
    :color "#868c8c"
    :margin-bottom (em 3)
    :text-align "center"
    :font-size (em 0.7)}
   [:a {:color "#4894d6" :text-decoration "none"}
    [:&:hover {:text-decoration "underline"}]]]


  (at-media {:max-width (px 736)}
            [:.text {:text-align "justify"}]
            [:section {:padding (em 1) :padding-bottom (em 3)}]
            [:h1 {:font-size (em 2.3)}]
            [:.grid [:li {:min-width "calc(100vw - 2em)"}]]
            [:.call-button {:margin-top (em 0.5)}])

  (at-media {:max-width (px 383)}
            [:section {:padding (em 1) :padding-bottom (em 2.5)}]))
