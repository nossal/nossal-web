  (ns nossal.web
   (:require [clojure.java.io :as io]
             [clojure.string :as s]
             [ring.util.response :as res]
             [clj-http.client :as client]
             [environ.core :refer [env]]
             [hiccup.core :as h]
             [hiccup.page :as page]
             [nossal.core :as core]))


(defn base [title css body req]
  (page/html5 {:⚡ true :lang "en"}
    [:head
      [:meta {:charset "UTF-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=0"}]
      [:meta {:name "keywords" :content "React, ML, Java, Clojure, ClojureScript, JavaScript, Python, ES6, Scala, programming, functional, HTML, CSS"}]
      [:meta {:name "description" :content "Rodrigo Nossal's personal website"}]
      [:meta {:name "theme-color" :content "#747f90"}]
      [:meta {:name "msapplication-TileColor" :content "#747f90"}]
      [:meta {:name "google-site-verification" :content (env :google-site-verification)}]
      [:link {:rel "canonical" :href (core/cannonical-url req)}]
      [:link {:rel "manifest" :href "/manifest.json"}]
      [:script {:async true :src "https://cdn.ampproject.org/v0.js"}]
      [:script {:async true :custom-element "amp-analytics" :src "https://cdn.ampproject.org/v0/amp-analytics-0.1.js"}]
      [:title title]
      [:style {:amp-custom true} (slurp (io/resource "public/css/screen.css")) css]
      [:style {:amp-boilerplate true} (slurp (io/resource "amp-css.css"))]
      [:noscript
        [:style {:amp-boilerplate true} " body{-webkit-animation:none;-moz-animation:none;-ms-animation:none;animation:none} "]]]
    [:body [:div.container (seq body)]
      [:amp-analytics {:type "googleanalytics"}
        [:script {:type "application/json"} (format "{\"vars\": {\"account\": \"%s\"},\"triggers\": {\"trackPageview\": {\"on\": \"visible\",\"request\": \"pageview\"}}}"
                                              (env :google-analytics))]]
      [:footer
        [:span.made "Handmade " [:a {:href "https://github.com/nossal/noss.al", :target "_blank" :rel "noopener"} "entirely"] " in "
          [:a {:href "http://clojure.org" :target "_blank" :rel "noopener"} "Clojure"] " and "
          [:span.heart " "] " at "
          [:a {:href "//pt.wikipedia.org/wiki/Gravata%C3%AD" :target "_blank" :rel "noopener"} "Grav."]]]]))


(defn index [req]
  (base "Nossal, Rodrigo Nossal" ""
    [[:header {:itemscope "" :itemtype "http://data-vocabulary.org/Person"}
       [:span.name [:h1 {:itemprop "name"} [:span "Rodrigo Nossal"]]]
       [:p.about-line "Full-Stack Developer"]]

     [:section#me [:div#tweetwidget]
       [:a.start {:href "#" :title "start"}
         [:svg {:width "60" :height "30" :xmlns "http://www.w3.org/2000/svg"}
           [:g#svg_1
             [:line {:y2 "24" :x2 "30" :y1 "5" :x1 "4" :stroke-linecap "round" :stroke-width "8"}]
             [:line {:y2 "24" :x2 "30" :y1 "5" :x1 "56" :stroke-linecap "round" :stroke-width "8"}]]]]]

     [:div.divisor]

     [:section#facebook]

     [:section#end [:div.end "Java, Python, JavaScript on weekdays and ES6, Scala, Clojure, Go, Perl on weekends."]]] req))


(defn dot [req]
  (if (s/includes? (get (:headers req) "user-agent") "curl")
    (do
      (client/post "http://www.google-analytics.com/collect"
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


(defn log [req]
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
