(ns nossal.db
  (:require [hugsql.core :as hugsql]
            [clojure.string :as s]
            [clojure.core.cache :as cache]
            [environ.core :refer [env]]
            [heroku-database-url-to-jdbc.core :as hdu]))

(def db {:classname "org.postgresql.Driver"
         :subprotocol "postgresql"
         :connection-uri (hdu/jdbc-connection-string (env :database-url))})

(hugsql/def-db-fns "sql/schema.sql")

(hugsql/def-sqlvec-fns "sql/schema.sql")

(defonce cache-store (atom (cache/lru-cache-factory {})))

(defn get-data [func key]
  (cache/lookup (swap! cache-store
                       #(if (cache/has? % key)
                          (cache/hit % key)
                          (cache/miss % key (func db key))))
                key))
