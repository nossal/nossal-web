(ns nossal.data
  (:require [clojure.data.json :as json]
            [environ.core :refer [env]]))


(def allowed-image-sizes #{48 72 76 96 120 144 152 180 192 512 1024})

(def pwa-manifest
  (json/write-str {:name "Nossal, Rodrigo Nossal"
                   :short_name "nossal"
                   :description "Rodrigo Nossal's personal website"
                   :display "fullscreen"
                   :theme_color "#747f90"
                   :background_color "#747f90"
                   :lang "pt-br"
                   :start_url "https://noss.al/"
                   :icons (map (fn [s]
                                 {:src (str "image/icon-" s ".png") :sizes (str s "x" s) :type "image/png"})
                               allowed-image-sizes)}))

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
                      "About.me"   "https://about.me/nossal"})

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

(def data-analytics (json/write-str {:vars {:account (env :google-analytics)}
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
(def coupom-codes {"cabify" {:code "rodrigon361"
                             :title "Cabify"
                             :url "https://cabify.com/i/rodrigon361"
                             :description "💲 Ganhe R$15,00 de desconto na sua primeira viagem! ✅"
                             :text [:p "Ganhe " [:span.value "R$15,00"] " de desconto na sua primeira corrida! &#x1F389;"
                                    [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, em sua primeira viagem. Ganhe até " [:span.value "100%"] " de desconto na sua viagem."]]}
                   "uber"   {:code "ubernossal"
                             :title "Uber"
                             :url "https://www.uber.com/invite/ubernossal"
                             :description "💲 Ganhe R$20,00 de desconto em 2 primeiras viagens! ✅"
                             :text [:p "Ganhe " [:span.value "R$10,00"]" de desconto nas suas " [:strong "2 primeiras"] " corridas! &#x1F389; "
                                    [:small "Este cupom pode ser utilizado duas vezes por pessoa, nas suas primeiras viagens. São " [:span.value "R$20,00"] " de desconto em duas viagens."]]}
                   "uber-1" {:code "0018f1"
                             :title "Uber"
                             :url "https://www.uber.com/invite/0018f1"
                             :description "💲 Ganhe R$20,00 de desconto em 2 primeiras viagens! ✅"
                             :text [:p "Ganhe " [:span.value "R$10,00"]" de desconto nas suas " [:strong "2 primeiras"] " corridas! &#x1F389; "
                                     [:small "Este cupom pode ser utilizado duas vezes por pessoa, nas suas primeiras viagens. São " [:span.value "R$20,00"] " de desconto em duas viagens."]]}
                   "uber-2" {:code "rafaeln1002ue"
                             :title "Uber"
                             :url "https://www.uber.com/invite/rafaeln1002ue"
                             :description "💲 Ganhe R$20,00 de desconto em 2 primeiras viagens! ✅"
                             :text [:p "Ganhe " [:span.value "R$10,00"]" de desconto nas suas " [:strong "2 primeiras"] " corridas! &#x1F389; "
                                     [:small "Este cupom pode ser utilizado duas vezes por pessoa, nas suas primeiras viagens. São " [:span.value "R$20,00"] " de desconto em duas viagens."]]}
                   "99"     {:code "RODRIGONOSSAL"
                             :title "99"
                             :url "http://ssqt.co/mecBdsK"
                             :description "💲 Ganhe R$20,00 de desconto na primeira corrida 99! ✅"
                             :text [:p "Ganhe " [:span.value "R$20,00"]" de desconto na sua " [:strong "primeira"] " corrida 99! &#x1F389; "
                                     [:small"Este cupom pode ser utilizado apenas uma vez por pessoa, em sua primeira corrida. Ganhe até " [:span.value "100%"] " de desconto na sua viagem."]]}
                   "99pop"  {:code "RhJemrf3mHV"
                             :title "99POP"
                             :url "https://d.99taxis.mobi/RhJemrf3mHV"
                             :description "💲 Ganhe R$15,00 de desconto na viagem corrida 99POP! ✅"
                             :text [:p "Ganhe " [:span.value "R$15,00"]" de desconto na sua " [:strong "primeira"] " corrida 99POP! &#x1F389; "
                                     [:small"Aceite meu convite e experimente já o 99POP. <br>Ganhe até " [:span.value "100%"] " de desconto na sua viagem."]]}})
