(ns nossal.shop.views
  (:require [clojure.string :as string]
            [re-frame.core :as rf]
            [nossal.shop.routes :as routes]))


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

(defn creditcard-input
  []
  [:div.creditcard
   [:input {:type "text"}]])


(defn admin-panel []
  [:h1 "Admin"])

(defn home-panel []
  [:div#app-container
   [:nav
    [:input {:type "search"}]
    [creditcard-input]
    [:a {:href (routes/url-for :admin)} "admin"]]
   [product-list]])

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :admin-panel [] [admin-panel])
(defmethod panels :default [] [:div "This is default route"])

(defn main-panel []
  (let [active-panel (rf/subscribe [:active-panel])]
    (fn []
      [panels @active-panel])))



