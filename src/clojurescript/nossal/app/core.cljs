(ns nossal.app.core
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [re-frame.core :as rf]
            [reagent.core :as reagent]
            [nossal.shop.core :as shop]))

(enable-console-print!)

(defn ^:export main
  []
  (rf/dispatch-sync [:initialise])
  (reagent/render [shop/app]
                  (.getElementById js/document "app")))

(main)
; (def routes ["/" {""          :home
;                   ":product"  :product
;                   "cart"      :cart}])

; (defn- parse-url [url]
;   (bidi/match-route routes url))

; (defn- dispatch-route [matched-route]
;   (let [panel-name (keyword (str (name (:handler matched-route)) "-panel"))]
;     (rf/dispatch [:set-active-panel panel-name])))

; (defn app-routes []
;   (pushy/start! (pushy/pushy dispatch-route parse-url)))

; (def url-for (partial bidi/path-for routes))
