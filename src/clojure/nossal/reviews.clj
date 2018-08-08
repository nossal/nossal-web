(ns nossal.reviews
  (:require [clojure.java.io :as io]
            [nossal.web :refer [base-html]]
            [environ.core :refer [env]]))

(defn reviews [req]
  (base-html "Reviews" [] []
    [{:async (true? (= "true" (env :production))) :charset "utf-8" :src "/js/app.js"}]
    [{:content (slurp (io/resource "public/css/app.css"))}]
    [[:div#container ""]]))
