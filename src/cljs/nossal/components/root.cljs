(ns nossal.components.root
  (:require [nossal.components.navigation :as nav]))


(defn root []
  [:div#root
   [nav/Navigation]
   [nav/Page]])
