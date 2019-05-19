(ns nossal.shop.components
  (:require [clojure.string :as string]))

(defn digit [number]
  [:div.digit {:style {:height "1.2em" :overflow "hidden"}}
   [:ul {:style {:margin-top "1.5em" :animation (str "number-" number " " (+ 500 (* number 60)) "ms 80ms normal forwards ease-in-out")}}
    (for [n (range 10)]
      ^{:key (str "d-" number "-" n)} [:li n])]])

(defn number-display [number]
  [:div.display {:style {:display "-webkit-box" :justify-content ""}}
    (for [n (map str number)]
     ^{:key (str "d-" n)} (digit n))])
  ; [:div (map #(vector :span %) (string/split number #""))])


; (:margin-top (str (* number 1.5 -1) "em"))
