(ns nossal.core
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reset :refer [reset]]))

(def bgcolor "#747f90")

(defstyles screen
  [reset]
  [:html {:background-color bgcolor}]
  [:body
    {:font-family "sans-serif"
     :font-size (px 16)
     :background-color "#747f90"
     :color "#FFF"}
    [:&:after {:background-image "linear-gradient(135deg, #d38312, #002f4b)"}]]

  [:header
    {:padding-top (em 10)
     :text-align "center"
    ;  :background-image "url('/images/backdrop.jpg')"
     :background-size "cover"
     :background-repeat "no-repeat"}
    [:p.catch {:color "#efc664"
               :letter-spacing "0.08em"}]]

  [:section
    {:text-align "center"}
    [:.terminal {:display "inline-block"
                 :margin-top (em 8);
                 :padding "1em"
                 :color "#4eb355"
                 :font-family "monospace"
                 :background-color "#282b35"}
      [:.string {:color "#d28e5d"}]
      [:.normal {:color "#c7c7c7"}]]]

  [:section.weeks
    {:background "white"
     :color "#555"}]

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
      {:border "0.1em solid #8b969f"
       :padding "0.3em 0.5em 0.2em"
       :display "inline-block"}]]

  [:.about-line {:font-size (em 1.1)
                 :font-weight 300
                 :color "#535d6d"
                 :display "inline-block"
                 :padding "0.5em 1em"
                 :background "#8b969f"}]
  [:.end {:color "#dcdcdc"}]

  [:footer
    {:font-family "PT Sans Narrow, Arial Narrow, Arial, sans-serif"
     :text-transform "uppercase"
     :text-align "center"
     :letter-spacing (em 0.06)
     :font-size (px 9)
     :color "#353535"
     :background-color "rgba(255, 255, 255, 0.12)"
     :line-height (px 20)
     :position "fixed"
     :width (percent 100)
     :bottom (px 0)}
    [:a {:color "#000"
         :text-decoration "none"
         :margin "0 0.3em"}
      [:&:hover {:text-decoration "underline"}]]
    [:.heart
      {:background-color "rgba(206, 36, 168, 0.72)"
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
                  :background-color "rgba(226, 47, 98, 0.5)"
                  :border-radius "0 3px 3px 0"}]]]

  (at-media {:max-width (px 736)}
    [:header {:padding-top (em 3)}]
    [:h1 {:font-size (em 3) :margin (em 0.5)}]))
