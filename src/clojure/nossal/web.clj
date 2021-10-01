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
            ; [nossal.styles :refer [bgcolor]]
            [nossal.db :refer [db]]
            [nossal.db :as data]
            [nossal.svg :refer [all-icons chevron-down nossal]]))



(defn- base [title meta links scripts styles body options]
  (page/html5
    {:lang (get options :lang "en") :⚡ (get options :amp false)}
    [:head
     (seq (slurp (io/resource "gtag.html")))
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
     [:title (str title " – nossal")]
     [:link {:rel "manifest" :href (get options :manifest "/manifest.json")}]
     [:link {:rel "mask-icon" :href "/safari-pinned-tab.svg" :color "#747f90"}]
     [:link {:rel "alternate" :href "https://noss.al" :hreflang "en-us"}]
     (map (fn [l] [:link l]) links)
     (map (fn [styl] [:style (styl :attr) (styl :content)]) styles)
     [:noscript
      (map (fn [node] node) (get options :noscript []))]]
    [:body
     [:script {:type "application/ld+json"} (core/to-json dat/data-website)]

     (seq body)

     [:a#tnet "π"]
     [:footer
      [:span.made "Handmade " (a-out "https://github.com/nossal/noss.al" "entirely") " with "]
      (a-out "https://clojure.org" "Clojure") " and "
      [:span.heart {:title "love" :role "img" :aria-label "love"} " "] " at "
      (a-out "https://pt.wikipedia.org/wiki/Gravata%C3%AD" "Aldeia dos Anjos 🇧🇷.")]
     (map (fn [s] [:script s]) scripts)]))

(defn base-html
  ([title meta links scripts styles body]
   (base-html title meta links scripts styles body {}))
  ([title meta links scripts styles body options]
   (base title meta links scripts styles body options)))

(defn- base-amp
  ([title meta links scripts styles body]
   (base-amp title meta links scripts styles body {}))
  ([title meta links scripts styles body options]
   (base
    title meta links
    (concat
     [{:async true :src "https://cdn.ampproject.org/v0.js"}]
     (when (= "true" (env :production))
       [{:async true :custom-element "amp-analytics" :src "https://cdn.ampproject.org/v0/amp-analytics-0.1.js"}])
     scripts)
    (concat
     [{:attr {:amp-boilerplate true} :content (slurp (io/resource "amp-css.css"))}]
     (map #(assoc % :attr {:amp-custom true}) styles))
    (into [[:amp-analytics {:type "gtag"}
            [:script {:type "application/json"} (core/to-json dat/data-analytics)]]]
          body)
    (merge options {:amp true
                    :noscript [[:style {:amp-boilerplate true} "body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none}"]]}))))


(defn index [req]
  (base-html
   "Nossal, Rodrigo"
   (let [description "Rodrigo Nossal Personal Website"]
     [{:name "description" :content description}
      {:name "keywords" :content "Python, Java, Clojure, Scala, Rust, ES6, JavaScript, ClojureScript, React, ML, programming, functional, HTML, CSS"}
      {:property "og:url" :content (core/cannonical-url req)}
      {:property "og:title" :content "Nossal, Rodrigo Nossal"}
      {:property "og:description" :content description}
      {:property "og:image" :content "https://noss.al/image/icon-1024.png"}])
   (concat [{:rel "canonical" :href (core/cannonical-url req)}] (favicons-attrs "icon"))
   [{:async (true? (= "true" (env :production))) :charset "utf-8" :src "/js/app.js"}]
   [{:content (slurp (io/resource "public/css/screen.css"))}]

   [[:script {:type "application/ld+json"} (core/to-json dat/data-person)]
    [:div.main
     [:nav [:ul.inline [:li [:a {:href "/cupons"} "goodies"]]]]
     [:article
      [:header
       [:h1 nossal]
       [:p.about-line [:span.accent {:title "Fool Stack"} "\"Full-Stack\""] " Web Developer"]]

      ;  [:section#me [:div#tweetwidget]
      ;   [:a.start {:href "#" :title "start"}
      ;    chevron-down]]

      [:div.divisor]

      ;  [:section "ノッサル・ロドリゴ"]

      [:section#about
       [:div.end
        [:span.java "Java"] ", "
        [:span.python "Python"] ", "
        [:span.js "JavaScript"] ", "
        [:span.swift "Swift"] " on weekdays and "
        [:span.clojure "Clojure/Script"] ", "
        [:span.rust "Rust"] " on weekends."]]

      [:section.foot
       [:a {:href "mailto:rodrigo@noss.al"} "rodrigo@noss.al"]
       (a-out (get dat/public-profiles "Twitter") "twitter")]]]]))

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
                    :tid (env :ga-tracking-id)
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


(defn iframe-demo [req]
  (base-html "IFRAME" [] [] [] []
             [[:iframe {:src "https://nossal.github.io/iframe.html?b" :width "100%" :height "600px"}]]))
