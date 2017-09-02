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
                                     :triggers {:trackPageview {:on "visible" :request "pageview"}
                                                :trackClickOnClick {:on "click"
                                                                    :selector "#get-coupom"
                                                                    :request "event"
                                                                    :vars {:eventCategory "ui-components"
                                                                           :eventAction "get-coupom"}}}}))
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
