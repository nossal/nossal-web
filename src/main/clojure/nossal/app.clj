(ns nossal.app
  (:require []))


(defroutes app
  (GET "/" []
    (index))

  (GET "/p" request
    (str request))

  (GET "/dot" request
    (dot request))

  (GET "/weekly" request
    (log request))

  (route/resources "/")

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))


(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
