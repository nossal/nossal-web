(ns styles.core
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import]]
            [styles.reset :refer [reset]]))

(defstyles screen
  [reset]
  [:body
    {:font-family "sans-serif"
     :font-size (px 16)
     :background-color "#747f90"
     :color "#FFF"}
    [:&:after {:background-image "linear-gradient(135deg, #d38312, #002f4b)"}]]

  [:header
    {:padding-top "10em"
     :text-align "center"
     :background-image "url('/images/backdrop.jpg')"
     :background-size "cover"
     :background-repeat "no-repeat"}
    [:p.catch {:color "#e0c583"
               :letter-spacing "0.08em"}]]
  [:.terminal {:display "inline-block"
               :padding "1em"
               :color "#4eb355"
               :font-family "monospace"
               :background-color "#282b35"}
    [:.string {:color "#d28e5d"}]]
  [:h1
    {:font-family "sans-serif"
     :font-size (em 3.5)
     :text-transform "uppercase"
     :letter-spacing "0.05em"
     :font-weight "100"
     :margin-bottom "0.3em"
     :text-align "center"
     :text-shadow "0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24)"}
    [:span
      {:border "0.1em solid white"
       :padding "0.3em 0.5em 0.2em"
       :display "inline-block"}]]

  [:.about-line {:letter-spacing (em 0.05)
                 :font-size (em 1.1)
                 :font-weight 100
                 :color "#ddbca8"}]

  [:footer
    {:font-family "PT Sans Narrow, Arial Narrow, Arial, sans-serif"
     :text-transform "uppercase"
     :text-align "center"
     :letter-spacing (em 0.06)
     :font-size (px 10)
     :color "#2d3036"
     :background-color "rgba(255, 255, 255, 0.3)"
     :line-height (px 20)
     :position "fixed"
     :width (percent 100)
     :bottom (px 0)}
    [:a {:color "#3137ad"
         :text-decoration "none"
         :margin "0 0.3em"}
      [:&:hover {:text-decoration "underline"}]]
    [:.heart
      {:background-color "rgba(255, 78, 78, 0.72)"
       :position "relative"
       :width (px 6)
       :height (px 10)
       :border-radius "3px 3px 0 0"
       :transform "rotate(315deg)"
       :display "inline-block"
       :margin-right (px 6)
       :margin-left (px 3)
       :margin-bottom (px -1)}
      [:&:before {:position "absolute"
                  :width (px 10)
                  :height (px 6)
                  :left 0
                  :bottom 0
                  :content "\"\""
                  :background-color "rgba(255, 78, 78, 0.72)"
                  :border-radius "0 3px 3px 0"}]]])
