(ns nossal.common
  (:require [nossal.colors :refer [bgcolor]]
            [garden.units :as gu :refer [px em rem percent]]))


(def body
  [:body
    {:background bgcolor
     :color "#FFF"
     :display "flex"
     :flex-direction "column"
     :min-height "100vh"}])

(def footer
  [:footer
    {:font-family "PT Sans Narrow, Arial Narrow, Arial, sans-serif"
     :text-transform "uppercase"
     :text-align "center"
     :position "fixed"
     :bottom 0
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
                  :border-radius "0 3px 3px 0"}]]])

(def egg
  [:a#tnet {:color "#4d5661"
            :font-size "11px"
            :display "block"
            :position "absolute"
            :cursor "default"
            :padding (px 2)
            :right (px 4)
            :bottom (px 20)}])


; (defn at-font-face [& {:as kwargs}]
;   (let [kwargs (->> (select-keys kwargs [:family :weight :style :eot :woff :svg])
;                     (remove (comp nil? second))
;                     (into {}))
;         font-attrs (select-keys kwargs [:family :weight :style])
;         srcs (select-keys kwargs [:eot :woff :svg])
;         url (cssfn :url)
;         format (cssfn :format)]
;     ["@font-face"
;      {:font font-attrs}
;      (when-not (empty? srcs)
;        {:src (for [[fmt uri] srcs]
;                [(url uri) (format (name fmt))])})]))
