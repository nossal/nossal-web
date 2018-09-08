(ns nossal.reviews
  (:require [clojure.java.io :as io]
            [nossal.web :refer [base-html]]
            [environ.core :refer [env]]))

(defn reviews [req]
  (base-html "Reviews" []
    [{:rel "stylesheet" :type "text/css" :href "/css/app.css"}]
    [{:async (true? (= "true" (env :production))) :charset "utf-8" :src "/js/app.js"}]
    []
    [[:div#app-container ""]]))
