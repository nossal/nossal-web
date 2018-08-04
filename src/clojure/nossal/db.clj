(ns nossal.db
  (:require [hugsql.core :as hugsql]
            [clojure.string :as s]
            [environ.core :refer [env]]
            [heroku-database-url-to-jdbc.core :as h]))


(def db {:classname "org.postgresql.Driver"
         :subprotocol "postgresql"
         :connection-uri (h/jdbc-connection-string (env :database-url))})


(hugsql/def-db-fns "sql/schema.sql")

(hugsql/def-sqlvec-fns "sql/schema.sql")
