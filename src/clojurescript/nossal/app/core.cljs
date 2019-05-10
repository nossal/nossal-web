(ns nossal.app.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [nossal.shop.core]
            [nossal.shop.routes :refer [app-routes]]
            [nossal.shop.views :as views]))

(enable-console-print!)


(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (rf/dispatch-sync [:initialise])
  (app-routes)
  (mount-root))


(init)
