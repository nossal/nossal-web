(ns nossal.reboot
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.selectors :refer [attr attr= abbr a audio input button before after defselector defpseudoclass defpseudoelement html not svg]]
            [garden.units :as gu :refer [px em percent]]))

(defpseudoelement -moz-focus-inner)
(defpseudoelement -webkit-inner-spin-button)
(defpseudoelement -webkit-search-decoration)
(defpseudoelement -webkit-outer-spin-button)
(defpseudoelement -webkit-search-cancel-button)
(defpseudoelement -webkit-file-upload-button)
(defselector *)

(defstyles reset
  [:* (* before) (* after)
   {:box-sizing "border-box"}]

  [:html
   {:font-family "sans-serif"
    :line-height "1.15"
    :text-size-adjust "100%"
    :-ms-overflow-style "scrollbar"
    :-webkit-tap-highlight-color "transparent"}]

  ["@-ms-viewport"
   {:width "device-width"}]

  [:article :aside :dialog :figcaption :figure :footer :header :hgroup :main :nav :section
   {:display "block"}]

  [:body
   {:margin 0
    :font-family "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\""
    :font-size (em 1)
    :font-weight "normal"
    :line-height 1.5
    :color "#212529"
    :text-align "left"
    :background-color "#fff"}]

  [(attr= :tabindex "-1")
   [:&:focus
    {:outline "none !important"}]]

  [:hr
   {:box-sizing "content-box"
    :height 0
    :overflow "visible"}]

  [:h1 :h2 :h3 :h4 :h5 :h6
   {:margin-top 0
    :margin-bottom (em 0.5)}]

  [:p
   {:margin-top 0
    :margin-bottom (em 1)}]

  [(abbr (attr :title)) (abbr (attr :data-original-title))
   {:text-decoration "underline dotted"
    :cursor "help"
    :border-bottom 0}]

  [:address
   {:margin-bottom "1em"
    :font-style "normal"
    :line-height "inherit"}]

  [:ol :ul :dl
   {:margin-top 0
    :margin-bottom (em 1)}]

  [:ol
   [:ol
    {:margin-bottom 0}]]
  [:ol
   [:ul
    {:margin-bottom 0}]]
  [:ul
   [:ul
    {:margin-bottom 0}]]
  [:ul
   [:ol
    {:margin-bottom 0}]]

  [:dt
   {:font-weight "bold"}]

  [:dd
   {:margin-bottom (em 0.5)
    :margin-left 0}]

  [:blockquote
   {:margin "0 0 1em"}]

  [:dfn
   {:font-style "italic"}]

  [:b :strong
   {:font-weight "bolder"}]

  [:small
   {:font-size "80%"}]

  [:sub :sup
   {:position "relative"
    :font-size "75%"
    :line-height 0
    :vertical-align "baseline"}]

  [:sub
   {:bottom (em -0.25)}]

  [:sup
   {:top (em -0.5)}]

  [:a
   {:color "#007bff"
    :text-decoration :none
    :background-color "transparent"
    :-webkit-text-decoration-skip "objects"}]

  [:a:hover
   {:color "#0056b3"
    :text-decoration "underline"}]

  [(a (not (attr :href)) (not (attr :tabindex)))
   {:color "inherit"
    :text-decoration :none}]

  [(a (not (attr :href)) (not (attr :tabindex)))
   [:&:hover :&:focus
    {:color "inherit"
     :text-decoration :none}]]

  [(a (not (attr :href)) (not (attr :tabindex)))
   [:&:focus
    {:outline 0}]]

  [:pre :code :kbd :samp
   {:font-family "monospace monospace"
    :font-size (em 1)}]

  [:pre
   {:margin-top 0
    :margin-bottom (em 1)
    :overflow "auto"
    :-ms-overflow-style "scrollbar"}]

  [:figure
   {:margin "0 0 1em"}]

  [:img
   {:vertical-align "middle"
    :border-style :none}]

  [(svg (not ":root"))
   {:overflow "hidden"}]

  [:a :area
   (button (attr= :role "button"))
   (input (not (attr "range"))) :label :select :summary :textarea
   {:-ms-touch-action "manipulation"
    :touch-action "manipulation"}]

  [:table
   {:border-collapse "collapse"}]

  [:caption
   {:padding-top (em 0.75)
    :padding-bottom (em 0.75)
    :color "#868e96"
    :text-align "left"
    :caption-side "bottom"}]

  [:th
   {:text-align "inherit"}]

  [:label
   {:display "inline-block"
    :margin-bottom (em 0.5)}]

  [:button
   {:border-radius 0}]

  [:button:focus
   {;:outline "1px dotted"
    :outline "5px auto -webkit-focus-ring-color"}]

  [:input :button :select :optgroup :textarea
   {:margin "0"
    :font-family "inherit"
    :font-size "inherit"
    :line-height "inherit"}]

  [:button :input
   {:overflow "visible"}]

  [:button :select
   {:text-transform :none}]

  [:button
   (html (attr= :type :button))
   (attr= :type :reset)
   (attr= :type :submit)
   {:-webkit-appearance "button"}]

  [(button -moz-focus-inner)
   ((attr= :type "button") -moz-focus-inner)
   ((attr= :type "reset") -moz-focus-inner)
   ((attr= :type "submit") -moz-focus-inner)
   {:padding 0
    :border-style :none}]

  [(input (attr= :type "radio")) (input (attr= :type "checkbox"))
   {:box-sizing "border-box"
    :padding 0}]

  [(input (attr= :type "date"))
   (input (attr= :type "time"))
   (input (attr= :type "datetime-local"))
   (input (attr= :type "month"))
   {:-webkit-appearance "listbox"}]

  [:textarea
   {:overflow "auto"
    :resize "vertical"}]

  [:fieldset
   {:min-width 0
    :padding 0
    :margin 0
    :border 0}]

  [:legend
   {:display "block"
    :width "100%"
    :max-width "100%"
    :padding 0
    :margin-bottom (em 0.5)
    :font-size (em 1.5)
    :line-height "inherit"
    :color "inherit"
    :white-space "normal"}]

  [:progress
   {:vertical-align "baseline"}]

  [((attr= :type "number") -webkit-inner-spin-button)
   ((attr= :type "number") -webkit-outer-spin-button)
   {:height "auto"}]

  [(attr= :type "search")
   {:outline-offset (px 2)
    :-webkit-appearance :none}]

  [((attr= :type "search") -webkit-search-cancel-button)
   ((attr= :type "search") -webkit-search-decoration)
   {:-webkit-appearance :none}]

  [(-webkit-file-upload-button)
   {:font "inherit"
    :-webkit-appearance "button"}]

  [:output
   {:display "inline-block"}]

  [:summary
   {:display "list-item"}]

  [:template
   {:display :none}]

  [(attr "hidden")
   {:display "none !important"}])
