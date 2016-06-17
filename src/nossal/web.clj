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


(def google-analytics [:script "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-11532471-6', 'auto');
  ga('send', 'pageview');"])


(defn base [title css body js-code]
  (page/html5 {:lang "pt-br"}
    [:head
      [:meta {:charset "UTF-8"}]
      [:meta {:http-equiv "cleartype" :content "on"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"}]
      [:meta {:name "keywords" :content "java, clojure, clojurescript, javascript, python, programming, development, html5, html, css, ajax, device orientation, instant, fast, dinherin, dinher.in"}]
      [:meta {:name "description" :content "Site pessoal de Rodrigo Nossal"}]
      [:meta {:name "google-site-verification" :content "ljuFr_e6LgEvNLMWoyGBPxvcmCQQQkwY28VpiKz3Eb8"}]
      [:title title]
      [:link {:rel "stylesheet" :href "/css/screen.css" :type "text/css"}]
      [:style css]]
    [:body (seq body)
    [:footer
      [:span.made "Handmade " [:a {:href "https://github.com/nossal/noss.al", :target "_blank"} "entirely"] " in "
        [:a {:href "http://clojure.org" :target "_blank"} "Clojure"] " and "
        [:span.heart " ♥ "] " at "
        [:a {:href "//pt.wikipedia.org/wiki/Gravata%C3%AD" :target "_blank"} "Grav."]]]
    [:script {:type "text/javascript"} js-code] google-analytics]))


(defn index []
  (base "Rodrigo Nossal" "lll"
    [[:header {:itemscope "" :itemtype "http://data-vocabulary.org/Person"}
      [:span.name [:h1 {:itemprop "name"} [:span "Rodrigo Nossal"]]]]
    [:section#me [:div#tweetwidget]
      [:a.start {:href "#"}
        [:svg {:width "60" :height "30" :xmlns "http://www.w3.org/2000/svg"}
          [:g#svg_1
            [:line {:y2 "24" :x2 "30" :y1 "5" :x1 "4" :stroke-linecap "round" :stroke-width "8"}]
            [:line {:y2 "24" :x2 "30" :y1 "5" :x1 "56" :stroke-linecap "round" :stroke-width "8"}]]]]]
    [:div.divisor]
    [:section#facebook]
    #_[:section#photos
      [:h2 "Minhas 	Fotos"]
      [:div#instaphotos.gallery]
      [:div#flickrphotos.gallery]]
    #_[:section#contact
      [:form {:method "POST"}
        [:h2 "Vamos conversar!"]
        [:input {:type "email" :name "email" :placeHolder "seu@email.com"}]
        [:textarea {:name "message" :rows "5"}]
        [:input {:type "submit" :value "Enviar Mensagem"}]]]
    #_[:section#end [:div.end "Fim."]]
    ]
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
        [:p.catch "A terminal configuration system"]]
       [:section [:div.terminal "eval " [:span.string "\"$(curl -fsL noss.al/dot)\""]]]] "")))

(defroutes app
  (GET "/" []
    (index))
  (GET "/p" request
    (str request))
  (GET "/dot" request
    (dot request))
  (route/resources "/")
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
