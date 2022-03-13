(ns nossal.index
  (:require [shadow.dom :as dom]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [nossal.routes :as routes]
            [nossal.components.root :refer [root]]
            [nossal.state.global]))


;; initialise re-frame by broadcasting event
(rf/dispatch-sync [:initialize])


(defn render []
  (r/render [root] (dom/by-id "app")))


(defn reload! []
  (println "(reload!)")
  (routes/start!)
  (render))


(defn main! []
  (println "(main!)")
  (reload!))


(main!)
