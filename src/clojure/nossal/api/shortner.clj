(ns nossal.api.shortner
  (:require [clojure.string :as s]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [ring.util.response :as res]
            [clj-http.client :as client]

            [nossal.util.base62 :refer [decode encode]]
            [nossal.db :as data]
            [nossal.db :refer [db]]))


(defn create-database []
  (-> (res/response (json/write-str (data/create-urls-table db)))
      (res/content-type "text/plain")))


(defn new-url [url]
  (-> (res/response (json/write-str (encode (:id (first (data/insert-url db {:url url}))))))
      (res/content-type "text/plain")))


(defn redirect [encoded-id]
  (let [url (:url (data/get-data data/url-by-id {:id (decode encoded-id)}))]
    (log/info url)
    (do
      (client/post "https://www.google-analytics.com/collect"
        {:form-params {:v "1"
                       :tid (env :ga-tracking-id)
                       :cid "555"
                       :t "pageview"
                       :dh "noss.al"
                       :dp (str "/sht/" encoded-id)
                       :dt (str "Page " (decode encoded-id))}})
      (res/redirect url))))
