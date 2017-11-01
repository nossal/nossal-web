(ns nossal.reboot-simple
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.selectors :refer [attr attr= abbr a audio input button before after defselector defpseudoclass defpseudoelement html not svg]]
            [garden.units :as gu :refer [px em rem percent]]))

(defpseudoelement -moz-focus-inner)
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
     :font-feature-settings "kern"
     :direction "ltr"
     :font-synthesis "none"
     :font-size (rem 1)
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
     :margin-bottom (rem 0.5)}]

  [:p
    {:margin-top 0
     :margin-bottom (rem 1)}]

  [(abbr (attr :title)) (abbr (attr :data-original-title))
   {:text-decoration "underline dotted"
    :cursor "help"
    :border-bottom 0}]

  [:ol :ul :dl
    {:margin-top 0
     :margin-bottom (rem 1)}]

  [:blockquote
    {:margin "0 0 1rem"}]

  [:dfn
    {:font-style "italic"}]

  [:b :strong
    {:font-weight "bolder"}]

  [:small
    {:font-size "80%"}]

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
     :margin-bottom (rem 1)
     :overflow "auto"
     :-ms-overflow-style "scrollbar"}]

  [:figure
    {:margin "0 0 1rem"}]

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

  [:th
    {:text-align "inherit"}]

  [:label
    {:display "inline-block"
     :margin-bottom (rem 0.5)}]

  [:button
    {:border-radius 0}]

  [:button:focus
    {:outline "5px auto -webkit-focus-ring-color"}]

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

  [:textarea
    {:overflow "auto"
     :resize "vertical"}]

  [(attr "hidden")
   {:display "none !important"}])
