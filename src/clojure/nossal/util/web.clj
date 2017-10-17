(ns nossal.util.web
  (:require [ring.util.response :as res]
            [clojure.java.io :as io]))


(def not-found
  (assoc (res/not-found (slurp (io/resource "404.html"))) :headers {"Content-Type" "text/html; charset=UTF-8"}))
