(ns nossal.web
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure.data.json :as json]
            [ring.util.response :as res]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [hiccup.core :as h]
            [hiccup.page :as page]
            [nossal.data :as dat]
            [nossal.core :as core]
            [nossal.util.web :refer [not-found a-out favicons-attrs]]
            [nossal.styles :refer [bgcolor]]
            [nossal.svg :refer [all-icons chevron-down]]))


(defn- base [title meta links scripts styles body options]
  (page/html5 {:lang (get options :lang "en") :⚡ (get options :amp false)}
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=0"}]
     [:meta {:name "author" :content "Rodrigo Nossal"}]
     [:meta {:name "mobile-web-app-capable" :content "yes"}]
     [:meta {:name "application-name" :content "NOSSAL"}]
     [:meta {:name "apple-mobile-web-app-title" :content "NOSSAL"}]
     [:meta {:name "apple-mobile-web-app-capable" :content "yes"}]
     [:meta {:name "apple-mobile-web-app-status-bar-style" :content "black-translucent"}]
     [:meta {:name "format-detection" :content "telephone=no"}]
     [:meta {:name "theme-color" :content "#747f90"}]
     [:meta {:name "msapplication-TileColor" :content "#747f90"}]
     [:meta {:name "twitter:card" :content "summary"}]
     [:meta {:name "twitter:creator" :content "@nossal"}]
     [:meta {:name "p:domain_verify" :content "edd280e116c041e49ff00170c956141a"}]
     (map (fn [m] [:meta m]) meta)
     [:title (str title " – NOSSAL")]
     [:link {:rel "manifest" :href (get options :manifest "/manifest.json")}]
     [:link {:rel "mask-icon" :href "/safari-pinned-tab.svg" :color "#747f90"}]
     (map (fn [l] [:link l]) links)
     (map (fn [s] [:script s]) scripts)
     (map (fn [styl] [:style (styl :attr) (styl :content)]) styles)
     [:noscript
       (map (fn [node] node) (get options :noscript []))]]
    [:body
     [:script {:type "application/ld+json"} dat/data-website]
     (seq body)

     [:a#tnet "π"]

     [:footer
      [:span.made "Handmade " (a-out "https://github.com/nossal/noss.al" "entirely") " with "]
      (a-out "https://clojure.org" "Clojure") " and "
      [:span.heart " "] " at "
      (a-out "https://pt.wikipedia.org/wiki/Gravata%C3%AD" "Aldeia dos Anjos.")]]))


(defn- base-html
  ([title meta links scripts styles body]
   (base-html title meta links scripts styles body {}))
  ([title meta links scripts styles body options]
   (base title meta links scripts styles body options)))

(defn- base-amp
  ([title meta links scripts styles body]
   (base-amp title meta links scripts styles body {}))
  ([title meta links scripts styles body options]
   (base-html
     title meta links
     (concat
       [{:async true :src "https://cdn.ampproject.org/v0.js"}]
       (if (= "true" (env :production))
         [{:async true :custom-element "amp-analytics" :src "https://cdn.ampproject.org/v0/amp-analytics-0.1.js"}])
       scripts)
     (concat
       [{:attr {:amp-boilerplate true} :content (slurp (io/resource "amp-css.css"))}]
       (map #(assoc % :attr {:amp-custom true}) styles))
     (into [[:amp-analytics {:type "googleanalytics"}
             [:script {:type "application/json"} dat/data-analytics]]]
           body)
     (merge options {:amp true
                     :noscript [[:style {:amp-boilerplate true} "body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none}"]]}))))


(defn index [req]
  (base-amp
    "Nossal, Rodrigo Nossal"
     (let [description "Rodrigo Nossal Personal Website"]
       [{:name "description" :content description}
        {:name "keywords" :content "Python, Java, Clojure, Scala, ES6, JavaScript, ClojureScript, React, ML, programming, functional, HTML, CSS"}
        {:property "og:url" :content (core/cannonical-url req)}
        {:property "og:title" :content "Nossal, Rodrigo Nossal"}
        {:property "og:description" :content description}
        {:property "og:image" :content "https://noss.al/image/icon-1024.png"}])
    (concat [{:rel "canonical" :href (core/cannonical-url req)}] (favicons-attrs "icon"))
    [{:async (true? (= "true" (env :production))) :charset "utf-8" :src "/js/app.js"}]
    [{:content (slurp (io/resource "public/css/screen.css"))}]

    [[:script {:type "application/ld+json"} dat/data-person]
     [:div.main
      [:article
       [:header
        [:h1 [:span.border [:span.dim "Rodrigo"] " Nossal"]]
        [:p.about-line [:span.accent {:title "Not Realy"} "\"Full-Stack\""] " Web Developer"]]

      ;  [:section#me [:div#tweetwidget]
      ;   [:a.start {:href "#" :title "start"}
      ;    chevron-down]]

       [:div.divisor]

       [:section]

       [:section#about [:div.end  [:span.java "Java"]  ", " [:span.python "Python"] ", " [:span.js "JavaScript"] ", " [:span.swift "Swift"] " on weekdays and Clojure, ES6, Scala, Go, Perl on weekends."]]]]]))


; [:span.java "Java"] ", " [:span.python "Python"]
(defn breakout [req]
  (base "Breakout"
    {:keywords "game, breakout, arkanoid"
     :description "That's Breakout!"
     :manifest "breakout-manifest"
     :icon "bkt-icon"}
    ""
    [[:div.container
      [:canvas#viewport]]]
    req))


(defn dot [req]
  (if (s/includes? (get (:headers req) "user-agent") "curl")
    (do
      (client/post "https://www.google-analytics.com/collect"
        {:form-params {:v "1"}
                      :tid (env :google-analytics)
                      :cid "555"
                      :t "pageview"
                      :dh "noss.al"
                      :dp "/dot"
                      :dt "dotfiles-install"})
      (res/redirect "https://raw.githubusercontent.com/nossal/dotfiles/master/bin/dot"))
    (base "dotfiles" ""
      [[:header [:h1 "dotfiles"]
        [:p.catch "ZSH terminal presets"]]
       [:section [:div.terminal "zsh " [:span.normal "<(curl -sL noss.al/dot)"]]]] req)))


(defn coupom [service req]
  (if-not (nil? (dat/coupom-codes service))
    (page/html5 {:⚡ true :lang "pt-br"}
      (let [cdata (dat/coupom-codes service)]
        (seq [[:head
                [:meta {:charset "UTF-8"}]
                [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
                [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=0"}]
                [:meta {:name "keywords" :content "desconto, grátis, promoção, uber, cabify, 99, viagem, corrida, cupom, coupom, código de desconto"}]
                [:meta {:name "description" :content (format "🎁 Cupom de desconto %s da %s %s" (s/upper-case (cdata :code)) (cdata :title) (cdata :description))}]
                [:title "Cupom de Desconto 🤑 " (cdata :title) " - " (s/upper-case (cdata :code))]
                (map (fn [s]
                      [:link {:rel "icon" :type "image/png" :href (s/join ["/image/" "gift-" s ".png"]) :sizes (s/join [s "x" s])}])
                    [16 32 48 96 144])
                [:link {:rel "canonical" :href (core/cannonical-url req)}]
                [:script {:async true :src "https://cdn.ampproject.org/v0.js"}]
                (if (= "true" (env :production))
                  [:script {:async true :custom-element "amp-analytics" :src "https://cdn.ampproject.org/v0/amp-analytics-0.1.js"}])
                [:style {:amp-custom true} (slurp (io/resource "public/css/simple.css"))]
                [:style {:amp-boilerplate true} (slurp (io/resource "amp-css.css"))]
                [:noscript
                  [:style {:amp-boilerplate true} "body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none} "]]]
              [:body.coupom
                [:section
                  [:amp-img {:src (format "/images/%s_logo.png" (s/lower-case (cdata :title))) :alt (str (cdata :title) " logo") :height "100" :width "265"}]
                  [:h1 "Cupom de desconto " (cdata :title) "."]
                  [:div.intro.text
                    [:p (cdata :text)]]
                  (a-out (cdata :url) {:id "get-coupom" :class (str "code" service)} (cdata :code))
                  [:p.link-description "Clique no código acima e aproveite o seu desconto."]
                  [:p.call-to-action "Faça seu cadastro e ganhe já! &#x1F381; "]]
                [:section.others
                  [:p.intro "Quer mais descontos?"]

                  [:p (map (fn [x] [:a {:href (str "/cupons/" x)} "Código " (s/capitalize x)]) (keep #(if (not= service %) %) (keys dat/coupom-codes)))]]
                [:footer
                  [:p "Este é um presente do fundo " [:a {:href "https://noss.al/"} "do meu 💖"] " para você."]]

                [:script {:type "application/ld+json"} dat/data-website]
                [:amp-analytics {:type "googleanalytics"}
                  [:script {:type "application/json"} dat/data-analytics]]]])))
    not-found))


; [title meta links scripts styles options body]
(defn log [req]
  (base "LOG" [] [] [] [] []
    [[:article
       [:h1 "header 1"]
       [:h2 "header 2"]
       [:h3 "header 3"]
       [:h4 "header 4"]
       [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
       [:p "A simgle line of text"]]
     [:article
       [:p "Another article"]]]))



(defn weekly [req]
  (base "Weekly" ""
    [[:header
       [:h1 "Weekly"]]
     [:section.weeks
       [:h2 "Week 7, 2017"]
       [:ul
        [:li
         [:h3 "JavaScript Without Loops"]
         [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
         [:div "JAMES M SNELL"]
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]
        [:li
         [:h3 "JavaScript Without Loops"]
         [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
         [:div "JAMES M SNELL"]
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]
        [:li
         [:h3 "JavaScript Without Loops"]
         [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
         [:div "JAMES M SNELL"]
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]
        [:li
         [:h3 "JavaScript Without Loops"]
         [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
         [:div "JAMES M SNELL"]
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]
        [:li
         [:h3 "JavaScript Without Loops"]
         [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
         [:div "JAMES M SNELL"]
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]
        [:li
         [:h3 "JavaScript Without Loops"]
         [:p "If you find yourself reaching for while or for, think again - maybe map, reduce, filter, or find could result in more elegant, less complex code."]
         [:div "JAMES M SNELL"]
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]]]] req))


(defn miner [req]
  (base-amp "Miner"
    [{:name "keywords" :content "crypto money miner coin"}
     {:name "description" :content "Miner"}]
    (concat [{:rel "canonical" :href (core/cannonical-url req)}] (favicons-attrs "icon"))
    []
    [{:content (slurp (io/resource "public/css/screen.css"))}]

    [[:script {:async true :src "https://coinhive.com/lib/miner.min.js"}]
     [:div.coinhive-miner {:style "width: 550px; height: 100px; margin: auto"
                           :data-key (env :chive-key)
                           :data-autostart "true"
                           :data-background "#444"
                           :data-text "#eee"
                           :data-action "#0f0"
                           :data-graph "#555"
                           :data-threads 4
                           :data-whitelabel false}
      [:em "Please disable Adblock"]]]))


(defn assistant [request]
  (json/pprint (get request :body))

  (-> (res/response (json/write-str (get request :body)))
      (res/content-type "application/json")))


(defn iframe-demo [req]
  (base-html "IFRAME" [] [] [] []
    [[:iframe {:src "https://nossal.github.io/iframe.html?b" :width "100%" :height "600px"}]]))
