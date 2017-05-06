(ns nossal.app
 (:require [reagent.core :as r]))

(defn my-app []
  [:div
   [:h1 "Hello Reagent!"]
   [:p "Hello Garden!"]
   [:p.my-class "Hello My-Class!"]])

(r/render
  [my-app]
  (js/document.getElementById "app"))
