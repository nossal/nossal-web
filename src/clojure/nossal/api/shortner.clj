(ns nossal.api.shortner
  (:require [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [environ.core :refer [env]]
            [ring.util.response :as res]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [clj-http.client :as client]

            [hiccup.page :as page]
            [hiccup.form :as form]

            [nossal.util.base62 :refer [decode encode]]
            [nossal.util.web :refer [not-found]]
            [nossal.db :as data]))

(defn create-database []
  (-> (res/response (json/write-str (data/create-urls-table data/db)))
      (res/content-type "text/plain")))

(defn new-url [request]
  (let [{:keys [params uri]} request
        param-url (get params "url")]
    (-> (res/response (json/write-str (encode (:id (first (data/insert-url data/db {:url param-url}))))))
        (res/content-type "text/plain"))))

(defn new-url-form [request]
  (page/html5
   [:div (form/form-to {:enctype "multipart/form-data"}
                       [:post "/short"]
                       (form/text-field "url")
                       (anti-forgery-field)
                       (form/submit-button "Submit"))]))

(defn redirect [encoded-id]
  (if-let [url (:url (data/get-data data/url-by-id {:id (decode encoded-id)}))]
    (do
      (log/info url)
      (client/post "https://www.google-analytics.com/collect"
                   {:form-params {:v "1"
                                  :tid (env :ga-tracking-id)
                                  :cid "555"
                                  :t "pageview"
                                  :dh "noss.al"
                                  :dp (str "/sht/" encoded-id)
                                  :dt (str "Page " (decode encoded-id))}})
      (res/redirect url))
    not-found))
