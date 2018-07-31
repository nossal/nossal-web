(ns nossal.data
  (:require [clojure.data.json :as json]
            [environ.core :refer [env]]))


(def allowed-image-sizes #{16 32 48 72 76 96 120 144 150 152 180 192 196 512 1024})

(def pwa-manifest
  (json/write-str
    {:name "Nossal, Rodrigo Nossal"
     :short_name "NOSSAL"
     :description "Rodrigo Nossal Personal Website"
     :display "fullscreen"
     :theme_color "#747f90"
     :background_color "#747f90"
     :orientation "portrait"
     :start_url "/#utm_source=web_app_manifest"
     :icons (map (fn [s]
                   {:src (str "image/icon-" s ".png") :sizes (str s "x" s) :type "image/png"})
                 (keep #(if (> % 32) %) allowed-image-sizes))}))

(def public-profiles {"Facebook"   "https://facebook.com/nossal"
                      "Twitter"    "https://twitter.com/nossal"
                      "Instagram"  "https://instagram.com/nossal"
                      "GitHub"     "https://github.com/nossal"
                      "linkedin"   "https://linkedin.com/in/nossal"
                      "Spotify"    "https://open.spotify.com/user/nossal"
                      "SoundCloud" "https://soundcloud.com/nossal"
                      "Last.fm"    "https://last.fm/user/nossal"
                      "Medium"     "https://medium.com/@nossal"
                      "Flickr"     "https://flickr.com/photos/nossal"
                      "SlideShare" "https://slideshare.net/nossal"
                      "Google+"    "https://google.com/+RodrigoNossal"
                      "Ello"       "https://ello.co/nossal"
                      "About.me"   "https://about.me/nossal"
                      "myspace"    "https://myspace.com/nossal"
                      "Pinterest"  "https://pinterest.com/thenossal"})

(def data-person (json/write-str {"@context" "https://schema.org"
                                  "@type" "Person"
                                  :name "Rodrigo Nossal"
                                  :url "https://noss.al"
                                  :sameAs (vals public-profiles)}))

(def data-website (json/write-str {"@context" "https://schema.org"
                                   "@type" "WebSite"
                                   :name "NOSSAL"
                                   :alternateName "Rodrigo Nossal"
                                   :url "https://noss.al"}))

(def data-analytics
  (json/write-str
    {:vars {:account (env :google-analytics)}
     :triggers {:trackPageview {:on "visible" :request "pageview"}
                :outboundLinks {:on "click"
                                :selector "a"
                                :request "event"
                                :vars {:eventCategory "outbound"
                                       :eventLabel "${outboundLink}"
                                       :eventAction "click"}}
                :trackClickOnCoupom {:on "click"
                                     :selector "#get-coupom"
                                     :request "event"
                                     :vars {:eventCategory "ui-components"
                                            :eventAction "get-coupom"}}
                :trackClickOnPI {:on "click"
                                 :selector "a#tnet"
                                 :request "event"
                                 :vars {:eventCategory "ui-components"
                                        :eventAction "the-net"}}

                :trackPWAInstall {:on "beforeinstallprompt"
                                  :request "event"
                                  :vars {:eventCategory "ui-components"
                                         :eventAction "PWA Install"}}}}))
