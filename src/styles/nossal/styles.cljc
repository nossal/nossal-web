(ns nossal.styles
  (:require [garden.def :refer [defstylesheet defstyles defkeyframes]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reboot :refer [reset]]))

(def bgcolor "linear-gradient(45deg, #837490, #6a7988)")

(defkeyframes DEFACTO
  [:0%   {:background-position "0% 50%"}]
  [:50%  {:background-position "100% 50%"}]
  [:100% {:background-position "0% 50%"}])


(defstyles screen
  DEFACTO reset
  [:body
    {:font-family "\"SF Pro Display\",\"SF Pro Icons\",\"Helvetica Neue\",\"Helvetica\",\"Arial\",sans-serif"
     :font-size (px 16)
     :background bgcolor
     :color "#FFF"
     :display "flex"
     :flex-direction "column"
     :min-height "100vh"}]

  [:nav {:background "#fff" :color "#444"}]

  [:.main
   {:flex 1
    :display "flex"}]

  [:article
   {:display "flex"
    :flex-direction "column"
    :margin (em 0.5)
    :width "100%"}]

  [:header
    {:padding-top (em 10)
     :text-align "center"
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
    {:font-size (em 3.5)
     :text-transform "uppercase"
     :letter-spacing "0.05em"
     :font-weight "300"
     :margin-bottom "0.3em"
     :text-align "center"
     :color "#ccc"
     :text-shadow "0 1px 5px rgba(0,0,0,0.05), 0 1px 4px rgba(0,0,0,0.20)"}
    [:>span
      {:background "linear-gradient(270deg,#76778c,#324f81,#961d99,#6f788a,#3b5789,#961d99,#6f788a,#315189,#961d99)"
       :background-size "700% 200%"
       :-webkit-text-fill-color "transparent"
       :-webkit-background-clip "text"
       :animation [[DEFACTO "120s linear infinite"]]}]
    [:span.border
      {:border "0.1em solid #858c9c"
       :padding "0.3em 0.5em 0.2em"
       :display "inline-block"}]]

  [:.accent {:color "rgba(130, 96, 195, 0.8)"}]
  [:.about-line {:font-size (em 1.1)
                 :font-weight 300
                 :color "rgba(119, 122, 147, 0.8)"
                 :display "inline-block"
                 :padding "0.5em 1em"
                 :text-shadow "1px 4px 4px #8b969f, 0 0 0 #000, 1px 4px 6px #8b969f"
                 :background "#8b969f"}]
  [:.end {:color "#d4d4d4"
          :text-shadow "1px 1px 3px rgba(0,0,0,0.2)"}]

  [:a#p {:color "#434245"
         :font-size "11px"
         :display "block"
         :position "absolute"
         :cursor "default"
         :padding (px 2)
         :right (px 2)
         :bottom (px 20)}]

  [:footer
    {:font-family "PT Sans Narrow, Arial Narrow, Arial, sans-serif"
     :text-transform "uppercase"
     :text-align "center"
     :letter-spacing (em 0.07)
     :font-size (px 9)
     :color "#bbb"
     :background-color "rgba(255, 255, 255, 0.1)"
     :line-height (px 20)
     :text-shadow "1px 1px 3px rgba(0, 0, 0, 0.2)"
     :width (percent 100)}
    [:a {:color "#ccc"
         :text-decoration "none"
         :margin "0 0.3em"}
      [:&:hover {:color "#ccc" :text-decoration "underline"}]]
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
    [:h1 {:font-size (em 3) :margin (em 0.5)}])

  (at-media {:max-height (px 400)}
    [:header {:padding-top (em 1)}]))

