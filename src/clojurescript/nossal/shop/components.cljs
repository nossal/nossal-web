(ns nossal.shop.components
  (:require [clojure.string :as string]))

(defn numeric? [value]
  (not= nil (re-matches #"[0-9]" value)))

(defn digit [number pos]
  (if (numeric? number)
    ^{:key (str "d-" pos)}
    [:ul.digit
     {:style {:margin-top "1.5em"
              :animation (str "number-" number " 900ms " (* pos 50) "ms normal forwards cubic-bezier(0.24, 0.07, 0, 0.97)")}}
     (for [n (range 10)]
       ^{:key (str "d-" pos "-" n)} [:li n])]
    ^{:key (str "d-" pos)} [:span.spacer number]))

(defn number-display [number]
  [:div.display {:style {:display "-webkit-box"
                         :height "1.2em"
                         :overflow "hidden"}}
   (map-indexed (fn [idx itm] (digit itm idx)) number)])

  ; [:div (map #(vector :span %) (string/split number #""))])

; (:margin-top (str (* number 1.5 -1) "em"))
