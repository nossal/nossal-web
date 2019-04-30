(ns nossal.shop.spec
  (:require [clojure.spec.alpha :as s]))


(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(def phone-regex #"^(?:(?:\+|00)?(55)\s?)?(?:\(?([1-9][0-9])\)?\s?)?(?:((?:9\d|[2-9])\d{3})\-?(\d{4}))$")
(def cep-regex #"^[0-9]{5}-[0-9]{3}$")

(s/def ::category string?)
(s/def ::categories (s/col-of ::category :distinct true :into []))

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

(s/def ::product (s/keys :req-un [::id
                                  ::market-price
                                  ::price
                                  ::title
                                  ::description
                                  ::characteristics
                                  ::images
                                  ::category
                                  ::stock-count
                                  ::available?]))
(s/def ::products (s/and
                    (s/map-of ::id ::product)
                    #(instance? PersistentTreeMap %)))

(s/def ::basket-item (s/keys :req-un [::product ::quantity]))
(s/def ::basket-itens (s/coll-of ::basket-item :kind vector? :distinct true :into []))
(s/def ::basket (s/keys :req-un [::id ::basket-itens]))


(s/def ::cep (s/def (s/and string? #(re-matches cep-regex %))))
(s/def ::street-address (s/keys :req [::street ::number]
                                :opt [::building ::apt]))

(s/def ::country #{:brazil})
(s/def ::address (s/keys :req [::cep
                               ::country
                               ::state
                               ::city
                               ::street-address]))

(s/def ::shipping-tracker string?)

(s/def ::order (s/keys :req-un [::id
                                ::basket
                                ::costumer
                                ::status
                                ::payment
                                ::shipping-tracker
                                ::address
                                ::creation-time]))
(s/def ::name (s/keys :req [::first-name ::last-name]))
(s/def ::costumer (s/keys :req [::id
                                ::name
                                ::email
                                ::address]
                          :opt [::phone
                                ::cpf]))

(s/def ::db (s/keys :req-un [::products ::basket]))
