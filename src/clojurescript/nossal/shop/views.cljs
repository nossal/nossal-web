(ns nossal.shop.views
  (:require [clojure.string :as string]
            [re-frame.core :as rf]
            [reagent.core :as reagent]
            [clojure.spec.alpha :as s]
            [nossal.shop.spec :as spec]
            [nossal.shop.components :refer [number-display]]
            [nossal.shop.routes :as routes]))

(def thinking
  [:svg {:xmlns "http://www.w3.org/2000/svg" :viewBox "0 0 511.757 511.757"}
   [:path {:fill "#ffcc4d" :d "M28.323 227.555c0 125.681 101.887 227.555 227.555 227.555 125.68 0 227.555-101.874 227.555-227.555C483.433 101.887 381.56 0 255.879 0 130.21 0 28.323 101.887 28.323 227.555"}]
   [:g {:fill "#664500"}
    [:path {:d "M218.986 149.334c0 19.641-12.742 35.555-28.445 35.555s-28.445-15.915-28.445-35.555c0-19.626 12.743-35.555 28.445-35.555s28.445 15.927 28.445 35.555M369.656 163.555c0 19.641-12.729 35.555-28.445 35.555-15.701 0-28.445-15.915-28.445-35.555 0-19.626 12.743-35.555 28.445-35.555 15.716 0 28.445 15.929 28.445 35.555M137.421 90.069a13.047 13.047 0 0 1-5.561-5.66c-3.073-6.102-.569-13.184 5.589-15.829 60.672-26.026 109.497-.611 111.545.484 6.158 3.285 8.647 10.639 5.561 16.412-3.073 5.76-10.525 7.765-16.683 4.523-1.748-.91-40.277-20.367-89.287.668-3.654 1.55-7.779 1.208-11.164-.598M209.556 278.358c-1.934-1.807-3.356-4.168-3.925-6.913-1.407-6.67 2.816-12.885 9.458-13.853 65.309-9.642 105.984 27.435 107.705 29.014 5.12 4.75 5.617 12.473 1.166 17.28-4.438 4.779-12.16 4.808-17.266.085-1.479-1.309-33.735-29.98-86.5-22.201-3.939.584-7.835-.796-10.638-3.413"}]
    [:path {:d "M290.452 143.402a13.15 13.15 0 0 1-5.148-6.03c-2.66-6.286.327-13.198 6.641-15.417 62.307-21.845 109.297 6.826 111.275 8.064 5.902 3.697 7.879 11.192 4.422 16.753-3.44 5.547-11.036 7.04-16.938 3.385-1.706-1.038-38.799-23.053-89.132-5.405-3.752 1.31-7.848.684-11.12-1.35"}]]
   [:path {:fill "#f4900c" :d "M245.581 499.655s17.992-5.845 20.324-19.229c2.46-13.81-8.875-16.597-8.875-16.597s14.805-2.959 16.669-19.57c1.749-15.645-12.26-19.385-12.26-19.385s13.795-5.689 14.45-21.888c.54-13.639-14.151-20.309-14.151-20.309s71.65-17.35 79.005-19.057c7.338-1.721 18.787-8.761 15.217-24.106-3.541-15.36-17.124-15.9-24.149-14.265-7.026 1.65-95.915 22.286-126.577 29.426-3.272.754-18.603 4.295-20.467 4.75-7.68 1.806-11.165-1.579-5.76-7.281 7.24-7.623 11.848-16.058 13.47-30.051 1.692-14.734-3.3-32.91-6.16-39.95-5.318-13.1-14.293-23.453-24.66-27.008-16.186-5.546-27.677 4.565-21.932 22.201 8.59 26.383 2.973 48-11.848 61.057-34.83 30.677-51.043 52.55-40.248 99.17 11.761 50.846 62.251 83.57 113.095 71.795 2.688-.6 44.857-9.703 44.857-9.703"}]])

(defn view [component]
  (reagent/create-class
   {:display-name (identity component)
    :component-did-mount
    (fn [] (println "I mounted"))
    :component-will-unmount
    (fn [] (println "bye!"))
    :reagent-render
    (fn []
      [:div#app-container {:class-name "teste"}
       component])}))

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
     [:div "card: " [:span.colorfull-text (number-display card-number-value)]]
     [:input {:type "text"
              :value card-number-value
              :on-change #(rf/dispatch [:set-card-number (-> % .-target .-value)])}]
     (when-not card-valid?
       [:span.emoji thinking])]))

(defn admin-panel []
  [:div
   [:nav [:a {:href (routes/url-for :home)} "Go Home"]]
   [:h1 "Admin!"]
   [:div "R$740,89 " [:span.used-to-be "R$989,99"]]
   [:form
    [:label.highlight "Credit Card"]
    [creditcard-input]]])

(defn home-panel []
  (view
   [:div#app-container
    [:nav
     [:input {:type "search"}]

     [:a {:href (routes/url-for :admin)} "admin"]]
    [product-list]]))

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :admin-panel [] [admin-panel])
(defmethod panels :default [] [:div "This is default route"])

(defn main-panel []
  (let [active-panel (rf/subscribe [:active-panel])]
    (fn []
      [panels @active-panel])))



