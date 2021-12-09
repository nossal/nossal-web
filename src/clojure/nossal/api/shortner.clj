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
  (data/drop-urls-table data/db)
  (-> (res/response (json/write-str (data/create-urls-table data/db)))
      (res/content-type "text/plain")))

(defn new-url [params]
    (-> (res/response (json/write-str (encode (:id (first (data/insert-url data/db {:name (:name params)
                                                                                    :tag (:tag params)
                                                                                    :url (:url params)}))))))
        (res/content-type "text/plain")))

(defn new-url-form [request]
  (page/html5
   [:div (form/form-to {:enctype "multipart/form-data"}
                       [:post "/short"]
                       (anti-forgery-field)
                       [:p (form/text-field {:placeholder "Name"} "name")]
                       [:p (form/text-field {:placeholder "Tag"} "tag")]
                       [:p (form/text-field {:placeholder "URL"} "url")]
                       [:p (form/submit-button "Submit")])]))

(defn redirect [encoded-id]
  (if-let [url (data/get-data data/url-by-id {:id (decode encoded-id)})]
    (do
      (log/info url)
      (client/post "https://www.google-analytics.com/collect"
                   {:form-params {:v "1"
                                  :tid (:tag url (env :ga-tracking-id))
                                  :cid "555"
                                  :t "pageview"
                                  :dh "noss.al"
                                  :dp (:name url (str "/go/" encoded-id))
                                  :dt (str "Page " (decode encoded-id) (:name url))}
                    :async? true}
                   (fn [response] (println "response is:" response))
                   (fn [exception] (println "exception message is: " (.getMessage exception))))
      (res/redirect (:url url)))
    not-found))
