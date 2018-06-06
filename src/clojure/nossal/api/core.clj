(ns nossal.api.core
  (:require [clojure.string :as s]
            [environ.core :refer [env]]
            [clojure.data.json :as json]
            [ring.util.response :as res]))


(defn debug [request]
  (prn (get request :body))

  (-> (res/response (json/write-str (get request :body)))
      (res/content-type "application/json")))
