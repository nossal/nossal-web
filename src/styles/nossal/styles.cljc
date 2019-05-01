(ns nossal.styles
  (:require [garden.def :refer [defstylesheet defstyles defkeyframes]]
            [garden.units :as gu :refer [px em rem percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [garden.selectors :refer [>]]
            [nossal.reboot-simple :refer [reset]]
            [nossal.common :refer [footer body egg]]))

(def bgcolor "linear-gradient(45deg, #837490, #6a7988)")

(defkeyframes DEFACTO
  [:0%   {:background-position "0% 50%"}]
  [:50%  {:background-position "100% 50%"}]
  [:100% {:background-position "0% 50%"}])

(defkeyframes show-mov
  [:from {:transform "translateY(10px)"
          :opacity 0}]
  [:to   {:transform "translateY(0)"
          :opacity 1}])

(defkeyframes dismiss-mov
  [:from {:transform "translateY(0)"
          :opacity 1}]
  [:to   {:transform "translateY(10px)"
          :opacity 0}])

(def show-anim [[show-mov "200ms ease-in forwards"]])
(def dismiss-anim [[dismiss-mov "200ms ease-out 5s forwards"]])

(defstyles screen
  DEFACTO
  dismiss-mov
  show-mov
  reset

  body
  footer
  egg

  [:nav {:background "#fff" :color "#444"}]

  [:p {:font-size (em 1.5)
       :font-weight 200
       :letter-spacing (rem 0.05)}]

  [:span.strike {:text-decoration "line-through"}]

  [:.main
   {:flex 1
    :display "flex"}]

  [:article
   {:display "flex"
    :flex-direction "column"
    ; :background "#fff"
    :color "#555"
    :margin "1em 0 0"
    :padding ".5em 1em"
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

  [(> :section :div)
   {:margin "0 1.5em"}]

  [:section.weeks
   {:background "white"
    :color "#555"}]

  [:h1
   {:font-size (em 3.5)
    :text-transform "uppercase"
    :letter-spacing (em 0.05)
    :line-height (em 1)
    :font-weight 300
    :margin-bottom (em 0.3)
    :margin-right (em -0.3)
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
                 :margin-top (rem 1)
                 :color "rgba(119, 122, 147, 0.8)"
                 :display "inline-block"
                 :padding "0.5em 1em"
                 :text-shadow "1px 4px 4px #8b969f, 0 0 0 #000, 1px 4px 6px #8b969f"
                 :background "#8b969f"}]
  [:.end {:color "#d4d4d4"
          :text-shadow "1px 1px 3px rgba(0,0,0,0.2)"}]

  [:alert-box {:display "block"
               :padding "5px 2px"
               :background "#666"
               :color "#eee"}]

  [:.online {:animation show-anim}]

  [:.offline {:animation dismiss-anim}]

  (at-media {:max-width (px 736)}
            [:header {:padding-top (em 3)}]
            [:h1 {:font-size (em 3) :margin (em 0.5)}])

  (at-media {:max-width (px 383)}
            [:header {:padding-top (em 1.5)}]
            [:h1 {:font-size (em 2.5)}]
            [:footer [:.made [:a {:display :none}]]]))

