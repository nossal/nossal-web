(ns nossal.util.web
  (:require [ring.util.response :as response]
            [clojure.java.io :as io]
            [image-resizer.core :refer [resize resize-to-width]]
            [image-resizer.format :as format]
            [nossal.data :as dat]))

(def not-found
  (assoc (response/not-found (slurp (io/resource "404.html"))) :headers {"Content-Type" "text/html; charset=UTF-8"}))


(defn resize-image [image width ext]
  (if (and (contains? dat/allowed-image-sizes width)
           (not (nil? (io/resource (str image "." ext)))))
    (let [file-name (str "/tmp/image-bucket/" image "-" width "." ext)]
      (when-not (.exists (io/file file-name))
        (io/make-parents file-name)
        (format/as-file (resize-to-width (io/resource (str image "." ext)) width) file-name :verbatim))

      (-> (response/response (io/input-stream file-name))
          (response/content-type (str "image/" ext))
          (response/header "Cache-Control" "public, max-age=31536000")))
    not-found))

(defn pwa-manifest []
  (response/content-type
    (response/response dat/pwa-manifest)
    "application/manifest+json"))

(defn a-out
  ([url text]
   (a-out url nil text))
  ([url attrs text]
   [:a (merge {:href url :data-vars-outbound-link url :target "_blank" :rel "noopener noreferrer"} attrs) text]))

(defn favicons-attrs [icon]
  (concat
    (map (fn [size]
            {:rel "icon" :type "image/png" :href (str "/image/" icon "-" size ".png") :sizes (str size "x" size)})
         [48 96 144 192])
    (map (fn [size]
            {:rel "apple-touch-icon" :href (str "/image/" icon "-" size ".png") :sizes (str size "x" size)})
         [76 120 152 180])))
