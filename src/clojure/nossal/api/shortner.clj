(ns nossal.api.shortner
  (:require [clojure.string :as s]
            [environ.core :refer [env]]
            [clojure.data.json :as json]
            [ring.util.response :as res]


(defn create-database []
  (-> (res/response (json/write-str (data/create-urls-table db)))
      (res/content-type "text/plain")))
