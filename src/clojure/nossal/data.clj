(ns nossal.data
  (:require [clojure.data.json :as json]
            [environ.core :refer [env]]))


(def data-website (json/write-str {"@context" "http://schema.org"
                                   "@type" "WebSite"
                                   :name "NOSSAL"
                                   :alternateName "Rodrigo Nossal"
                                   :url "http://noss.al"}))

(def public-profiles #{"http://facebook.com/nossal"
                       "http://instagram.com/nossal"
                       "http://twitter.com/nossal"
                       "http://github.com/nossal"
                       "http://soundcloud.com/nossal"
                       "http://linkedin.com/in/nossal"
                       "http://flickr.com/photos/nossal"
                       "http://last.fm/user/nossal"
                       "http://slideshare.net/nossal"
                       "http://about.me/nossal"
                       "http://medium.com/@nossal"
                       "http://ello.co/nossal"})

(def data-person (json/write-str {"@context" "http://schema.org"
                                  "@type" "Person"
                                  :name "Rodrigo Nossal"
                                  :url "http://noss.al"
                                  :sameAs public-profiles}))

(def data-analytics (json/write-str {:vars {:account (env :google-analytics)}
                                     :triggers {:trackPageview {:on "visible" :request "pageview"}}}))
