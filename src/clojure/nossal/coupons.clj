(ns nossal.coupons
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [ring.util.response :as res]
            [environ.core :refer [env]]
            [hiccup.core :as h]
            [hiccup.page :as page]
            [nossal.data :as dat]
            [nossal.core :as core]
            [nossal.util.web :refer [not-found a-out]]))


(def coupon-codes
  {"cabify" {:code "rodrigon361"
             :title ["Cabify"]
             :url "https://cabify.com/i/rodrigon361"
             :description "💲 Ganhe R$20,00 de desconto na sua primeira viagem! ✅"
             :text [:p "Ganhe " [:span.value "R$20,00"] " de desconto na sua primeira corrida! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, em sua primeira viagem. Ganhe até " [:span.value "100%"] " de desconto na sua viagem."]]}
   "uber"   {:code "ubernossal"
             :title ["Uber" "#1"]
             :url "https://www.uber.com/invite/ubernossal"
             :description "💲 Ganhe R$20,00 de desconto em 2 primeiras viagens! ✅"
             :text [:p "Ganhe " [:span.value "R$10,00"] " de desconto nas suas " [:strong "2 primeiras"] " corridas! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado duas vezes por pessoa, nas suas primeiras viagens. São " [:span.value "R$20,00"] " de desconto em duas viagens."]]}
   "uber-1" {:code "0018f1"
             :title ["Uber" "#2"]
             :url "https://www.uber.com/invite/0018f1"
             :description "💲 Ganhe R$10,00 de desconto em 2 primeiras viagens! ✅"
             :text [:p "Ganhe " [:span.value "R$10,00"] " de desconto nas suas " [:strong "2 primeiras"] " corridas! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado duas vezes por pessoa, nas suas primeiras viagens. São " [:span.value "R$20,00"] " de desconto em duas viagens."]]}
   "uber-2" {:code "rafaeln1002ue"
             :title ["Uber" "#3"]
             :url "https://www.uber.com/invite/rafaeln1002ue"
             :description "💲 Ganhe R$10,00 de desconto em 2 primeiras viagens! ✅"
             :text [:p "Ganhe " [:span.value "R$10,00"] " de desconto nas suas " [:strong "2 primeiras"] " corridas! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado duas vezes por pessoa, nas suas primeiras viagens. São " [:span.value "R$20,00"] " de desconto em duas viagens."]]}
   "99"     {:code "BRE747Z4"
             :title ["99"]
             :url "https://d.99app.com/rZy8mU5EEiu13?shareChannel=more"
             :description "💲 Ganhe R$10,00 de desconto na primeira corrida 99 (99POP também)! ✅"
             :text [:p "Ganhe " [:span.value "R$10,00"] " de desconto na sua " [:strong "primeira"] " viagem no 99! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, em sua primeira corrida. Ganhe até " [:span.value "100%"] " de desconto na sua viagem."]
                    [:p.warn [:span "Seja rápido!"] " Você tem " [:strong "até 15 dias"] " para fazer a sua primeira viagem antes do cupom expirar. 😨"]]}
   "picpay" {:code "PKTA9D"
             :title ["PicPay"]
             :url "http://www.picpay.com/convite?!PKTA9D"
             :description "💲 Ganhe de volta os primeiros R$10,00 que você gastar! ✅"
             :text [:p "Crie sua conta com meu código e ganhe de volta os primeiros " [:span.value "R$10,00"] " que você " [:strong "gastar"] "! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Com o PicPay você pode pagar seus " [:strong "amigos, boletos, recarregar o celular, Steam"] " e muito mais."]
                    [:p.warn [:span "Mas atenção"] ", você só ganha se fizer um pagamento em " [:strong "até 7 dias depois do seu cadastro."]]]}})


(defn coupon [service req]
  (if-not (nil? (coupon-codes service))
    (page/html5 {:⚡ true :lang "pt-br"}
                (let [cdata (coupon-codes service)]
                  (seq [[:head
                         [:meta {:charset "UTF-8"}]
                         [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
                         [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=0"}]
                         [:meta {:name "keywords" :content "desconto, grátis, promoção, uber, cabify, 99, viagem, corrida, cupom, coupon, código de desconto"}]
                         [:meta {:name "description" :content (format "🎁 Cupom de desconto %s da %s %s" (s/upper-case (cdata :code)) (first (cdata :title)) (cdata :description))}]
                         [:title "Cupom de Desconto 🤑 " (first (cdata :title)) " - " (s/upper-case (cdata :code))]
                         (map (fn [s]
                                [:link {:rel "icon" :type "image/png" :href (s/join ["/image/" "gift-" s ".png"]) :sizes (s/join [s "x" s])}])
                              [16 32 48 96 144])
                         [:link {:rel "canonical" :href (core/cannonical-url req)}]
                         [:link {:rel "alternate" :href (str "https://noss.al/cupons/" service)  :hreflang "pt-br"}]
                         [:script {:async true :src "https://cdn.ampproject.org/v0.js"}]
                         [:script {:async true :custom-element "amp-analytics" :src "https://cdn.ampproject.org/v0/amp-analytics-0.1.js"}]
                         [:style {:amp-custom true} (slurp (io/resource "public/css/simple.css"))]
                         [:style {:amp-boilerplate true} (slurp (io/resource "amp-css.css"))]
                         [:noscript
                          [:style {:amp-boilerplate true} "body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none} "]]]
                        [:body.coupon
                         [:section
                          [:amp-img {:src (format "/images/%s_logo.png" (s/lower-case (first (cdata :title)))) :alt (str (first (cdata :title)) " logo") :height "100" :width "265"}]
                          [:h1 "Cupom de desconto " (first (cdata :title)) "."]
                          [:div.intro.text
                           [:p (cdata :text)]]
                          (a-out (cdata :url) {:id "get-coupon" :data-vars-couponvalue 10 :data-vars-coupon service :class (str "code" service)} (cdata :code))
                          [:p.link-description "Clique no código acima e aproveite o seu desconto."]
                          [:p.call-to-action "Faça seu cadastro e ganhe já! &#x1F381; "]]
                         [:section.others
                          [:p.intro "Quer mais descontos?"]

                          [:p (map (fn [x] [:a {:href (str "/cupons/" x)} "Cupom " (first ((coupon-codes x) :title)) " " [:span (rest ((coupon-codes x) :title))]]) (keep #(if (not= service %) %) (shuffle (keys coupon-codes))))]]
                         [:footer
                          [:p "Este é um presente do fundo " [:a {:href "https://noss.al/"} "do meu " [:span {:title "coração"} "❤️"]] " para você."]]

                         [:script {:type "application/ld+json"} (core/to-json dat/data-website)]
                         [:script {:type "application/ld+json"} (core/to-json (dat/breadcrumbs (str "cupons/" service)))]
                         [:amp-analytics {:type "gtag" :data-credentials "include"}
                          [:script {:type "application/json"} (core/to-json dat/data-analytics)]]]])))
    not-found))

