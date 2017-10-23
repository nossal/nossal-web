(ns nossal.util.web
  (:require [ring.util.response :as response]
            [clojure.java.io :as io]
            [image-resizer.core :refer [resize]]
            [image-resizer.format :as format]
            [nossal.data :as dat]))

(def not-found
  (assoc (response/not-found (slurp (io/resource "404.html"))) :headers {"Content-Type" "text/html; charset=UTF-8"}))


(defn resize-image [image size format]
  (if (and (contains? dat/allowed-image-sizes size)
           (not (nil? (io/resource (str image "." format)))))

    (-> (response/response (format/as-stream (resize (io/resource (str image "." format)) size size) format))
        (response/content-type (str "image/" format))
        (response/header "Cache-Control" "immutable, public, max-age=31536000"))
    not-found))

(defn pwa-manifest []
  (-> (response/response dat/pwa-manifest)
      (response/content-type "application/json")))
