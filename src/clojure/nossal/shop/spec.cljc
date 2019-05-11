(ns nossal.shop.spec
  (:require [clojure.spec.alpha :as s]))

(defn valid-luhn? [pan]
  (letfn [(char->int [c] (- (int c) (int \0)))
          (mod-10? [n] (zero? (mod n 10)))
          (sum-luhn-pair [[m n]]
            (+ m
               ([0 2 4 6 8 1 3 5 7 9] (or n 0))))]
    (->> pan
         reverse
         (map char->int)
         (partition-all 2)
         (map sum-luhn-pair)
         (apply +)
         mod-10?)))


(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(def phone-regex #"^(?:(?:\+|00)?(55)\s?)?(?:\(?([1-9][0-9])\)?\s?)?(?:((?:9\d|[2-9])\d{3})\-?(\d{4}))$")
(def cep-regex #"^[0-9]{5}-[0-9]{3}$")

(s/def ::category string?)
(s/def ::categories (s/coll-of ::category :distinct true :into []))

(s/def ::id int?)
(s/def ::quantity (s/and int? #(> % 0)))
(s/def ::price (s/and float? #(> % 0)))
(s/def ::market-price (s/and float? #(> % 0)))
(s/def ::title string?)
(s/def ::name string?)
(s/def ::email (s/and string? #(re-matches email-regex %)))
(s/def ::phone (s/and string? #(re-matches phone-regex %)))
(s/def ::description string?)
(s/def ::stock-count (s/and int? #(>= % 0)))
(s/def ::available? boolean?)
(s/def ::images (s/coll-of uri? :kind vector? :distinct true :into []))
(s/def ::characteristics (s/and
                          (s/map-of ::name ::description)
                          #(instance? PersistentTreeMap %)))

(s/def ::variant (s/keys :req [::id
                               ::name
                               ::price
                               ::stock-count
                               ::available?
                               ::images]))
(s/def ::variants (s/coll-of ::variant :kind vector? :distinct true :into []))
(s/def ::product-condition #{:used :brand-new :almost-new})
(s/def ::stock-location #{:brazil :china :usa :sweden :switzerland :local})

(s/def ::product (s/keys :req-un [::id
                                  ::market-price
                                  ::price
                                  ::title
                                  ::description
                                  ::characteristics
                                  ::images
                                  ::category
                                  ::stock-count
                                  ::product-condition
                                  ::stock-location
                                  ::available?]
                         :opt-un [::variants]))
(s/def ::products (s/and
                   (s/map-of ::id ::product)
                   #(instance? PersistentTreeMap %)))

(s/def ::basket-item (s/keys :req-un [::product ::quantity]))
(s/def ::basket-itens (s/coll-of ::basket-item :kind vector? :distinct true :into []))
(s/def ::basket (s/keys :req-un [::id ::basket-itens]))

(s/def ::cep (s/and string? #(re-matches cep-regex %)))
(s/def ::street-address (s/keys :req [::street ::number]
                                :opt [::complement]))
(s/def ::country #{:brazil :usa})
(s/def ::currency #{:BRL :USD :MXN :COP :CLP :ARS :PEN :EUR :PYN :UYU :VEB :VEF :GBP})
(s/def ::address (s/keys :req [::cep
                               ::country
                               ::state
                               ::city
                               ::street-address]))


(s/def ::shipping-tracking string?)

(s/def ::order (s/keys :req-un [::id
                                ::basket
                                ::customer
                                ::status
                                ::payment
                                ::shipping-tracking
                                ::address
                                ::creation-time]))
(s/def ::card-number (s/and string? valid-luhn?))
(s/def ::creadit-card (s/keys :req [::card-number
                                    ::holder
                                    ::expiration-date
                                    ::security-code
                                    ::brand
                                    ::save-card]))

(s/def ::payment-type #{:creadit-card :debit-card :boleto})
(s/def ::payment (s/keys :req-un [::payment-type
                                  ::amount
                                  ::currency
                                  ::country
                                  ::installments
                                  ::soft-descriptor]))


(s/def ::name (s/keys :req [::first-name ::last-name]))
(s/def ::customer (s/keys :req [::id
                                ::name
                                ::email
                                ::address]
                          :opt [::phone
                                ::birthdate
                                ::cpf]))

(s/def ::db (s/keys :req-un [::products ::basket]))
