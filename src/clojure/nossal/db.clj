(ns nossal.db
  (:require [hugsql.core :as hugsql]
            [environ.core :refer [env]]))


(def db {:classname "org.postgresql.Driver"
         :subprotocol "postgresql"
         :connection-uri (env :database-url)})


(hugsql/def-db-fns "sql/schema.sql")

(hugsql/def-sqlvec-fns "sql/schema.sql")
