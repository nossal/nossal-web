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

  [:section :header
    {:margin-top "10em"
     :text-align "center"}
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

  [:footer
    {:font-family "sans-serif"
     :text-transform "uppercase"
     :text-align "center"
     :letter-spacing (em 0.03)
     :font-size (px 10)
     :color "#5d6571"
     :background-color "rgba(255, 255, 255, 0.3)"
     :line-height (px 20)
     :position "absolute"
     :width (percent 100)
     :bottom (px 0)}
    [:a {:color "#5570a0"
         :text-decoration "none"
         :margin "0 0.5em"}
      [:&:hover {:text-decoration "underline"}]]
    [:.heart
      {:color "#ff4e4e"
       :font-family "sans-serif"
       :font-size (px 14)}]])
