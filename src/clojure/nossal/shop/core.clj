(ns nossal.shop.core
  (:require [clojure.java.io :as io]
            [nossal.web :refer [base-html]]
            [environ.core :refer [env]]
            [biscuit.core :as digest]))

(defn shop [req]
  (base-html "SHOP"
             []
             [{:rel "stylesheet" :type "text/css" :href "/css/app.css"}]
             [{:async (true? (= "true" (env :production))) :charset "utf-8" :src "/js/app.js"}]
             []
             [[:div#app-container ""]]))


(defn to-qr-string [data]
  (apply str
         (map (fn [[k v]]
                (str (name k) (if (map? (second v))
                                (str (first v) (to-qr-string (second v)))
                                (str (first v) (second v)))))
              data)))

(defn size-and-field [field]
  (vec [(format "%02d"
                (count
                 (if (map? field)
                   (to-qr-string field)
                   field)))
        field]))

(def currency {:BRL 986
               :USD 840
               :MXN 484
               :COP 170
               :CLP 152
               :ARS 32
               :PEN 604
               :EUR 978
               :PYN 0
               :UYU 858
               :VEB 862
               :VEF 937
               :GBP 826})


(defn qr-code [product payment]
  (array-map
   :00 (size-and-field "01")
   :01 (size-and-field "12")
   :26 (size-and-field {:00 (size-and-field "Cielo")
                        :01 (size-and-field "1108999686")
                        :02 (size-and-field "20091303")})
   :52 (size-and-field "8999")
   :53 (size-and-field "986")
   :54 (size-and-field "000000001299")
   :58 (size-and-field "BR")
   :59 (size-and-field "Nossal's Shop")
   :60 (size-and-field "GRAVATAI RS")
   :80 (size-and-field {:00 (size-and-field "\"https://www.cielo.com.br/qrcode\"")
                        :01 (size-and-field "13050329197F190A")
                        :02 (size-and-field "150518113349")
                        :03 (size-and-field "1000")
                        :04 (size-and-field "0001")
                        :05 (size-and-field "00")
                        :06 (size-and-field "01")})
   :63 "04"))

(defn gen-qr [data]
  (let [qr (to-qr-string data)]
    (str qr (clojure.string/upper-case (Integer/toString (digest/crc16-ccitt qr) 16)))))

; (as-file (from (gen-qr qr-code) :correction L :size [250 250]) "qr.png")
(as-file (from (vcard "John Doe"
                :email "john.doe@example.org"
                :address "John Doe Street 1, 5678 Doestown"
                :title "Mister"
                :company "John Doe Inc."
                :phonenumber "1234"
                :website "www.example.org")
               :correction L :size [250 250]) "qqr.png")
