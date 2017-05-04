(ns nossal.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as res]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [hiccup.core :as h]
            [hiccup.page :as page]))


(def google-analytics [:script (slurp (io/resource "analytics-code.js"))])


(defn base [title css body js-code]
  (page/html5 {:lang "pt-br"}
    [:head
      [:meta {:charset "UTF-8"}]
      [:meta {:http-equiv "cleartype" :content "on"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"}]
      [:meta {:name "keywords" :content "java, clojure, clojurescript, javascript, python, programming, development, html5, html, css, ajax, device orientation, instant, fast, dinherin, dinher.in"}]
      [:meta {:name "description" :content "Rodrigo Nossal's personal website"}]
      [:meta {:name "theme-color" :content "#747f90"}]
      [:meta {:name "google-site-verification" :content "ljuFr_e6LgEvNLMWoyGBPxvcmCQQQkwY28VpiKz3Eb8"}]
      [:title title]
      (page/include-css "/css/screen.css")
      [:style css]]
    [:body (seq body)
     [:footer
       [:span.made "Handmade " [:a {:href "https://github.com/nossal/noss.al", :target "_blank"} "entirely"] " in "
         [:a {:href "http://clojure.org" :target "_blank"} "Clojure"] " and "
         [:span.heart " "] " at "
         [:a {:href "//pt.wikipedia.org/wiki/Gravata%C3%AD" :target "_blank"} "Grav."]]]
     [:script {:type "text/javascript"} js-code] google-analytics]))


(defn index []
  (base "Nossal, Rodrigo Nossal" ""
    [[:header {:itemscope "" :itemtype "http://data-vocabulary.org/Person"}
      [:span.name [:h1 {:itemprop "name"} [:span "Rodrigo Nossal"]]]
      [:p.about-line "Full-Stack Developer"]
      [:section#me [:div#tweetwidget]
        [:a.start {:href "#"}
          [:svg {:width "60" :height "30" :xmlns "http://www.w3.org/2000/svg"}
            [:g#svg_1
              [:line {:y2 "24" :x2 "30" :y1 "5" :x1 "4" :stroke-linecap "round" :stroke-width "8"}]
              [:line {:y2 "24" :x2 "30" :y1 "5" :x1 "56" :stroke-linecap "round" :stroke-width "8"}]]]]]
      [:div.divisor]
      [:section#facebook]
      [:section#end [:div.end "Java, Python, JavaScript on weekdays and ES6, Scala, Clojure on weekends."]]]]
    "console.log('Oi!')"))


(defn dot [req]
  (if (s/includes? (get (:headers req) "user-agent") "curl")
    (do
      (client/post "http://www.google-analytics.com/collect"
        {:form-params {:v "1"
                       :tid "UA-11532471-6"
                       :cid "555"
                       :t "pageview"
                       :dh "noss.al"
                       :dp "/dot"
                       :dt "dotfiles-install"}})
      (res/redirect "https://raw.githubusercontent.com/nossal/dotfiles/master/bin/dot"))
    (base "dotfiles" ""
      [[:header [:h1 "dotfiles"]
        [:p.catch "ZSH terminal presets"]]
       [:section [:div.terminal "zsh " [:span.normal "<(curl -sL noss.al/dot)"]]]] "")))


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
         [:a.from {:href "http://sasd.com"} "JavaScript Weekly"]]]]] ""))

