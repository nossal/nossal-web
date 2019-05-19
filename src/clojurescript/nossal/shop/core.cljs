(ns nossal.shop.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [clojure.string :as string]
            [reagent.core :as reagent]
            [reagent.format :as format]
            [re-frame.core :as rf]
            [nossal.shop.data :refer [products-db]]))

(rf/reg-event-db
 :initialise
 (fn [_ _]
   {:products-db products-db
    :name "re-frame"
    :card-number "1234"}))

(rf/reg-event-db
 :set-card-number
 (fn [db [_ card-number]]
    (assoc db :card-number card-number)))

(rf/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(rf/reg-sub
 :card-number
 (fn [db _]
   (:card-number db)))

(rf/reg-sub
 :products
 (fn [db _]
   (:products-db db)))

(rf/reg-sub-raw
 :name
 (fn [db]
   (reaction (:name @db))))

(rf/reg-sub-raw
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))


; (defn product [p]
;   [:div
;     [:div [:a {:on-click #()} (:name p)]]
;     [:ul (map-indexed (fn [i x]
;                         [:li {:key i}
;                           [:img {:src x :width 100} ]]) (:images p))]])


; (defn products []
;   [:div [:ul (map-indexed (fn [i p]
;                             [:li {:key i} (product p)]) (:products @app-state))]])
