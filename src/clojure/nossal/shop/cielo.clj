(ns nossal.shop.cielo
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))


(def cielo-host "https://apisandbox.cieloecommerce.cielo.com.br/1")
(def merchant-id "3985f046-f401-49f7-8359-70b98af8898f")
(def merchant-key "DJHEAILARJJSRKFFBDVRAKRJJCBTYZKBLVQPOGNE")

(defn uuid []
  (str (java.util.UUID/randomUUID)))


(defn handle-response [req-fn url options]
  (let [response (req-fn url options)]
    (case (:status response)
      401 "access denied"
      400 (json/read-str (:body response)))))

(defn options []
  {:headers {:MerchantId merchant-id
             :MerchantKey merchant-key
             :content-type "application/json"
             :RequestId (uuid)}
   :content-type :json
   :accept :json
   :throw-exceptions false})

(defn get-sales []
  (http/get (str cielo-host "/sales/")
            (options)))


(defn post [url options]
  (handle-response http/post url options))

(defn get-qrcode [payment]
  (post
   (str cielo-host "/sales/")
   (conj (options) {:body (json/json-str payment)})))



