(ns nossal.coupons
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [hiccup.page :as page]
            [nossal.data :as dat]
            [nossal.core :as core]
            [nossal.util.web :refer [a-out]]))


(def coupon-codes
  {"ifood"  {:code "119VK2SYX2"
             :title ["iFood"]
             :url "https://ifoodbr.onelink.me/F4X4/mgm?mgm_code=119VK2SYX2"
             :description "💲 Ganhe R$20,00 na sua primeira compra! ✅"
             :text [:p "Ganhe " [:span.value "R$15,00"] " de desconto na " [:strong "sua primeira"] " compra! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, na sua primeira compra. São " [:span.value "R$15,00"] " de desconto pra você aproveitar."]]}
   "netflix" {:code "https://www.netflix.com/n/06f1f35b-799e-41af-8cf5-e2d82c479048"
              :title ["Netflix"]
              :url "https://www.netflix.com/n/06f1f35b-799e-41af-8cf5-e2d82c479048"
              :description "💲 Ganhe R$20,00 na sua primeira compra! ✅"
              :text [:p "Ganhe " [:span.value "R$15,00"] " de desconto na " [:strong "sua primeira"] " compra! " [:ruby "🎉 " [:rt "ta-da!"]]
                     [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, na sua primeira compra. São " [:span.value "R$15,00"] " de desconto pra você aproveitar."]]}
   "picpay" {:code "PKTA9D"
             :title ["PicPay"]
             :url "http://www.picpay.com/convite?!PKTA9D"
             :description "💲 Ganhe de volta os primeiros R$10,00 que você gastar! ✅"
             :text [:p "Crie sua conta com meu código e ganhe de volta os primeiros " [:span.value "R$10,00"] " que você " [:strong "gastar"] "! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Com o PicPay você pode pagar seus " [:strong "amigos, boletos, recarregar o celular, Steam"] " e muito mais."]
                    [:p.warn [:span "Seja rápido"] ", você só ganha se fizer um pagamento em " [:strong "até 7 dias depois do seu cadastro."]]]}
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
             :title ["99" "#1"]
             :url "https://d.99app.com/0Yw8Gq2?shareChannel=more"
             :description "💲 Ganhe R$10,00 de desconto na primeira corrida 99 (99POP também)! ✅"
             :text [:p "Ganhe " [:span.value "R$18,00"] " de desconto na sua " [:strong "primeira"] " viagem no 99! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, em sua primeira corrida. Ganhe até " [:span.value "100%"] " de desconto na sua viagem."
                     [:p.warn [:span "Seja rápido!"] " Você tem " [:strong "até 15 dias"] " para fazer a sua primeira viagem antes do cupom expirar. 😨"]]]}
   "99-1"   {:code "GMKH2MST"
             :title ["99" "#2"]
             :url "https://d.99app.com/tQj2VoR?shareChannel=more"
             :description "💲 Ganhe R$10,00 de desconto na primeira corrida 99 (99POP também)! ✅"
             :text [:p "Ganhe " [:span.value "R$18,00"] " de desconto na sua " [:strong "primeira"] " viagem no 99! " [:ruby "🎉 " [:rt "ta-da!"]]
                    [:small "Este cupom pode ser utilizado apenas uma vez por pessoa, em sua primeira corrida. Ganhe até " [:span.value "100%"] " de desconto na sua viagem."
                     [:p.warn [:span "Seja rápido!"] " Você tem " [:strong "até 15 dias"] " para fazer a sua primeira viagem antes do cupom expirar. 😨"]]]}})


(defn coupon-name [{title :title :as coupon}]
  [:span "Cupom " (first title) " " (when-let [sub (next title)] [:sup sub])])

(defn coupon-base [req service page]
  (page/html5
   {:⚡ true :lang "pt-br"}
   [:head
    [:meta {:charset "UTF-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=0"}]
    [:meta {:name "keywords" :content "desconto, grátis, promoção, viagem, corrida, cupom, código de desconto"}]
    [:meta {:name "description" :content (:description page)}]
    [:title (:title page)]
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
    (:body page)
    [:footer
     [:p "Este é um presente do fundo " [:a {:href "https://noss.al/"} "do meu " [:span {:title "coração"} "❤️"]] " para você."]]

    [:script {:type "application/ld+json"} (core/to-json dat/data-website)]
    [:script {:type "application/ld+json"} (core/to-json (dat/breadcrumbs (str "cupons/" service)))]
    [:amp-analytics {:type "gtag" :data-credentials "include"}
     [:script {:type "application/json"} (core/to-json dat/data-analytics)]]]))

(defn coupon-index [req]
  (coupon-base
   req ""
   {:title "Cupons de Desconto"
    :description "cupons desconto promoção uber ifood 99 cabify picpay netflix"
    :body [:section [:div {:style "font-size: 4em"} "🎁"] [:h1 "Cupons"]
           [:div.intro.text
            [:p "Confira abaixo uma lista de cupons que podem ser úteis pra você. " [:ruby "🎉 " [:rt "ta-da!"]]]]
           [:ul.column.grid
            (map (fn [name]
                   [:li [:a {:href (str "/cupons/" name)}
                         (coupon-name (coupon-codes name))]])
                 (keys coupon-codes))]]}))

(defn coupon [service req]
  (if-let [service-data (coupon-codes service)]
    (coupon-base
     req service
     {:title (str "Cupom de Desconto 🤑 " (first (service-data :title)) " - " (s/upper-case (service-data :code)))
      :description (format "🎁 Cupom de desconto %s da %s %s" (s/upper-case (service-data :code)) (first (service-data :title)) (service-data :description))
      :body [:div
             [:section
              [:amp-img {:src (format "/images/%s_logo.png" (s/lower-case (first (service-data :title)))) :alt (str (first (service-data :title)) " logo") :height "100" :width "265"}]
              [:h1 "Cupom de desconto " (first (service-data :title)) "."]
              [:div.intro.text
               [:p (service-data :text)]]

              [:input {:type "text"
                       :class "get-coupon"
                       :readonly true
                       :id "copy"
                       :data-vars-couponvalue "10"
                       :data-vars-coupon (service-data :code)
                       :value (service-data :code)}]

              [:p.link-description "Clique / toque no código acima para copiar, e aproveite o seu desconto."]
              (a-out (service-data :url) {:class "call-to-action"} "Faça aqui seu cadastro e ganhe já! &#x1F381; ")]
             [:section.others
              [:p.intro "Quer mais descontos?"]

              [:div.grid (map (fn [name]
                         [:a {:href (str "/cupons/" name)}
                          (coupon-name (coupon-codes name))])
                       (keep #(when (not= service %) %) (shuffle (keys coupon-codes))))]]]})
    coupon-index))
