(ns nossal.data
  (:require [clojure.data.json :as json]
            [environ.core :refer [env]]))


(def data-website (json/write-str {"@context" "http://schema.org"
                                   "@type" "WebSite"
                                   :name "NOSSAL"
                                   :alternateName "Rodrigo Nossal"
                                   :url "http://noss.al"}))

(def public-profiles {"Facebook"   "http://facebook.com/nossal"
                      "Twitter"    "http://twitter.com/nossal"
                      "Instagram"  "http://instagram.com/nossal"
                      "GitHub"     "http://github.com/nossal"
                      "linkedin"   "http://linkedin.com/in/nossal"
                      "Spotify"    "https://open.spotify.com/user/nossal"
                      "SoundCloud" "http://soundcloud.com/nossal"
                      "Last.fm"    "http://last.fm/user/nossal"
                      "Medium"     "http://medium.com/@nossal"
                      "Flickr"     "http://flickr.com/photos/nossal"
                      "SlideShare" "http://slideshare.net/nossal"
                      "Google+"    "http://google.com/+RodrigoNossal"
                      "Ello"       "http://ello.co/nossal"
                      "About.me"   "http://about.me/nossal"})

(def data-person (json/write-str {"@context" "http://schema.org"
                                  "@type" "Person"
                                  :name "Rodrigo Nossal"
                                  :url "http://noss.al"
                                  :sameAs (vals public-profiles)}))

(def data-analytics (json/write-str {:vars {:account (env :google-analytics)}
                                     :triggers {:trackPageview {:on "visible" :request "pageview"}}}))
