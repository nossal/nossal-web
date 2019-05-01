(ns nossal.shop.core
  (:require [clojure.string :as string]
            [reagent.core :as reagent]
            [reagent.format :as format]
            [re-frame.core :as rf]
            [nossal.shop.data :refer [products-db]]))

(rf/reg-event-db
 :initialise
 (fn [_ _]
   {:products-db products-db}))

; (defn product [p]
;   [:div
;     [:div [:a {:on-click #()} (:name p)]]
;     [:ul (map-indexed (fn [i x]
;                         [:li {:key i}
;                           [:img {:src x :width 100} ]]) (:images p))]])


; (defn products []
;   [:div [:ul (map-indexed (fn [i p]
;                             [:li {:key i} (product p)]) (:products @app-state))]])


(rf/reg-sub
 :products
 (fn [db _]
   (:products-db db)))

(defn price-display [value]
  [:div.price [:span.currency "R$"] (let [values (string/split value #"\.")]
                                      [:span (first values) "," [:span.cents (second values)]])])

(defn product-card
  []
  (fn [{:keys [code name price images]}]
    [:li.product-card
     [:img {:src (first images)}]
     (price-display price)
     [:div name]]))

(defn product-list
  []
  (let [products @(rf/subscribe [:products])]
    [:ul#product-list
     (for [product products]
       ^{:key (:code product)} [product-card product])]))

(defn app []
  [:div#app-container
   [:nav
    [:input {:type "search"}]]
   [product-list]])
