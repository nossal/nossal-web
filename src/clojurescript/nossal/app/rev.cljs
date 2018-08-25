(ns nossal.app.rev
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]))
            ; [cljs.spec :as s]))

(def app-state
  (reagent/atom
    {:partners [{:name "banggood", :code "?p=GD121317165513201712"}]
     :products
      [{:code 1
        :name "TS100 Soldering Iron"
        :descr ""
        :link "https://www.banggood.com/MINI-TS100-Digital-OLED-Programmable-Interface-DC-5525-Soldering-Iron-Station-Built-in-STM32-Chip-p-984214.html"
        :partner "banggood"
        :images ["https://img.staticbg.com/thumb/view/upload/2015/12/552/38.jpg"
                 "https://img.staticbg.com/thumb/view/upload/2015/12/SKU252598/15.jpg"
                 "https://img.staticbg.com/thumb/view/oaupload/banggood/images/7B/44/6cf50167-7cd1-480c-9848-af37daffcfce.jpg"]}
       {:code 2
        :name "Women Sexy Jacquard Brief"
        :descr ""
        :link "https://www.banggood.com/Low-Waist-Sexy-Jacquard-Briefs-Seamless-Thin-Hollow-Breathable-Panties-p-1227228.html"
        :partner "banggood"
        :images ["https://img.staticbg.com/thumb/view/oaupload/banggood/images/E7/9C/c5a5725b-90e6-49e0-bd91-c861c90eeb15.jpg"
                 "https://img.staticbg.com/thumb/view/upload/2015/12/SKU252598/15.jpg"
                 "https://img.staticbg.com/thumb/view/oaupload/banggood/images/91/57/5998560e-e194-469d-8a65-cac4deaed624.jpg"]}]}))



(defn products []
  [:div [:ul
          (for [p (:products @app-state)]
            [:li [:a {:on-click #()} (:name p)]])]])


(defn product [p]
  [:div
    [:div (:name p)]
    [:ul (map (fn [x] [:li [:img {:src x}]]) (:images p))]])


(defn appp []
  [:div "minha app."
    (products)])


(defn mount-app []
  (reagent/render
    [appp]
    (js/document.getElementById "app-container")))
