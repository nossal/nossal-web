(ns nossal.app.rev
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))
            ; [cljs.spec :as s]))

(defn appp []
  [:div "minha app"])


(defn mount-app []
  (reagent/render
    [appp]
    (js/document.getElementById "app-container")))


