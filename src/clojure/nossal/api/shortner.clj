(ns nossal.api.shortner
  (:require [clojure.string :as s]
            [environ.core :refer [env]]
            [clojure.data.json :as json]
            [ring.util.response :as res]
            [nossal.db :as data]
            [nossal.db :refer [db]]))


(defn create-database []
  (-> (res/response (json/write-str (data/create-urls-table db)))
      (res/content-type "text/plain")))


(defn new-url [url]
  (-> (res/response (json/write-str (data/insert-url db {:url url})))
      (res/content-type "text/plain")))
