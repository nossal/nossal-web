(ns nossal.shop
  (:require [garden.def :refer [defstylesheet defstyles defkeyframes]]
            [garden.units :as gu :refer [px em percent]]
            [garden.color :as color :refer [rgba]]
            [garden.stylesheet :refer [at-import at-media]]
            [nossal.reboot-simple :refer [reset]]
            [nossal.common :refer :all]))


(defn number-margin [number]
  (* number 1.5 -1))

(defn number-start-margin [number]
  (+ (number-margin number) (* 2 1.5)))

(defn force-em [value]
  (let [str-value (str value)]
    (str
     (if (= \0 (last str-value))
       (apply str (butlast (butlast str-value)))
       str-value)
     "em")))

(defn number-anim [number]
  [[:from {:margin-top (force-em (number-start-margin number))}]

   [:to   {:margin-top (force-em (number-margin number))}]])


(defkeyframes number-0
  (number-anim 0))
(defkeyframes number-1
  (number-anim 1))
(defkeyframes number-2
  (number-anim 2))
(defkeyframes number-3
  (number-anim 3))
(defkeyframes number-4
  (number-anim 4))
(defkeyframes number-5
  (number-anim 5))
(defkeyframes number-6
  (number-anim 6))
(defkeyframes number-7
  (number-anim 7))
(defkeyframes number-8
  (number-anim 8))
(defkeyframes number-9
  (number-anim 9))


(defstyles shop
  reset
  egg
  footer
  defaults
  number-0 number-1 number-2 number-3 number-4 number-5 number-6 number-7 number-8 number-9
  [:html {:height "100%" :overflow "hidden"}]
  [:body {:height "100%"
          :overflow "hidden"
          :background "#000"
          :margin 0}]

  [:#app {:background "#efece8"
          :height "100%"
          :border-radius (em 2)
          :overflow "hidden"
          :scrollbar-width "none"
          :overflow-y "scroll"}]
  [:#product-list {:display "flex"
                   :flex-flow "row wrap"}]
  [:.product-card {:background "white"
                   :flex-grow 1
                   :display "inline-block"
                   :color "#444"
                   :margin (em 1)
                   :padding (em 1)
                   :max-width (px 200)
                   :border-radius (em 1)}
   [:img {:width "100%"}]
   [:.price {:font-size (em 1.5)}
    [:.currency {:font-size (em 0.5)
                 :vertical-align "text-top"}]
    [:.cents {:font-size (em 0.5)
              :color "#aaa"}]]]
  [:.emoji [:svg {:width (px 32) :vertical-align "top"}]]
  [:.highlight {:background "linear-gradient(to right, #fce4ed, #ffe8cc)"
                :padding "2px 6px"
                :border-radius (px 2)
                :color "#505050"}]
  [:.colorfull-text {:background "linear-gradient(to right, #ff8a00, #da1b60)"
                     :background-clip "text"
                     :display "inline-block"
                     :text-fill-color "transparent"
                     :box-decoration-break "clone"}]
  [:.used-to-be {:position "relative"
                 :color "#fd854f"}
   [:&:before {:position "absolute"
               :content "\"\""
               :left 0
               :top (percent 45)
               :right 0
               :transform "rotate(-20deg)"
               :border-top "2px solid"
               :border-color "inherit"}]])
