(ns nossal.core
  (:require [clojure.data.json :as json]))


(defn requested-url [req]
  " returns: scheme, server, uri, querystring "
  (list* [(name (:scheme req))
          (:server-name req)
          (:uri req)
          (if-let [qs (:query-string req)] (str "?" qs) (str ""))]))


(defn cannonical-url [req]
  (apply format "https://%s%s" (rest (requested-url req))))


(defn to-json [data]
  (json/write-str data))
