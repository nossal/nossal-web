(ns nossal.shop.views
  (:require [clojure.string :as string]
            [re-frame.core :as rf]
            [clojure.spec.alpha :as s]
            [nossal.shop.spec :as spec]
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

(defn creditcard-input []
  (let [card-number-value @(rf/subscribe [:card-number])
        card-valid? (s/valid? ::spec/card-number card-number-value)]
   [:div.creditcard
     [:p (str "card: " card-number-value)]
     [:input {:type "text"
              :value card-number-value
              :on-change #(rf/dispatch [:set-card-number (-> % .-target .-value)])}]
     (when-not card-valid?
      [:p "Error"])]))

(defn admin-panel []
  [:div#app-container
   [:h1 "Admin!"]
   [:form
    [:label "Credit Card"]
    [creditcard-input]]])

(defn home-panel []
  [:div#app-container
   [:nav
    [:input {:type "search"}]

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



