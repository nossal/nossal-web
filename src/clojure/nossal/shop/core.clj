(ns nossal.shop.core
  (:require [clojure.java.io :as io]
            [nossal.web :refer [base-html]]
            [environ.core :refer [env]]))

(defn shop [req]
  (base-html "SHOP" []
    [{:rel "stylesheet" :type "text/css" :href "/css/app.css"}]
    [{:async (true? (= "true" (env :production))) :charset "utf-8" :src "/js/app.js"}]
    []
    [[:div#app-container ""]]))

