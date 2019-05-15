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
     :margin (em 0.5)
     :display "inline-block"
     :border "1px solid #c292c9"
     :text-decoration "none"
     :border-radius "1em"
     :padding "0.3em 0.6em"}
    [:&:after {:content "'🚀'" :padding-left (em 0.3)}]]]

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
  [:#get-coupon
   {:border "1px solid"
    :border-radius (px 2)
    :max-width (px 360)
    :padding ".2em .4em"
    :color "#0070c9"
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
    :color "#d527c7"
    :margin-bottom (em 3)
    :text-align "center"
    :font-size (em 0.7)}
   [:a {:color "#570084" :text-decoration "none"}
    [:&:hover {:text-decoration "underline"}]]] (at-media {:max-width (px 736)}
                                                          [:.text {:text-align "justify"}]
                                                          [:section {:padding (em 1) :padding-bottom (em 3)}]
                                                          [:h1 {:font-size (em 2.3)}]
                                                          [:footer {:margin-top (em 1)}]
                                                          [:.call-button {:margin-top (em 0.5)}])

  (at-media {:max-width (px 383)}
            [:section {:padding (em 1) :padding-bottom (em 2.5)}]
            [:#get-coupon.code99 {:font-size (em 2)}]
            [:#get-coupon.code99pop {:font-size (em 2)}]))

