(ns nossal.shop.cielo
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]))


(defn handle-response [req-fn url options]
  (let [response (req-fn url options)]
    (case (:status response)
      (200 201) (json/read-str (:body response))
      401 (log/error "Access Denied")
      (log/error (str "HTTP: " (:status response)) (json/read-str (:body response))))))

(defmacro get [url options]
  (handle-response http/get url options))
(defmacro post [url options]
  (handle-response http/post url options))

(def cielo-host "https://apisandbox.cieloecommerce.cielo.com.br/1")
(def merchant-id "3985f046-f401-49f7-8359-70b98af8898f")
(def merchant-key "DJHEAILARJJSRKFFBDVRAKRJJCBTYZKBLVQPOGNE")

(defn uuid []
  (str (java.util.UUID/randomUUID)))

(defn options []
  {:headers {:MerchantId merchant-id
             :MerchantKey merchant-key
             :content-type "application/json"
             :RequestId (uuid)}
   :content-type :json
   :accept :json
   :throw-exceptions false})

(defn get-sales []
  (get (str cielo-host "/sales/"
            (options))))

(defn get-qrcode [payment]
  (post (str cielo-host "/sales/")
        (conj (options) {:body (json/json-str payment)})))
