(ns nossal.db
  (:require [hugsql.core :as hugsql]
            [clojure.string :as s]
            [environ.core :refer [env]]))


(def db {:classname "org.postgresql.Driver"
         :subprotocol "postgresql"
         :connection-uri (s/replace (env :database-url) #"postgres" "jdbc:postgresql")})


(hugsql/def-db-fns "sql/schema.sql")

(hugsql/def-sqlvec-fns "sql/schema.sql")
