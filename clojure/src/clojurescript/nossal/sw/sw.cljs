(ns nossal.sw.sw)

(def app-cache-name "nossal-app-cache")
(def files-to-cache ["/js/app.js" "/"])

(defn parse-url [url]
  (zipmap [:url :proto :host :port :rest]
          (first (re-seq #"^(.*:)//([A-Za-z0-9\-\.]+)(:[0-9]+)?(.*)$" url))))

(defn- purge-old-cache [e]
  (-> js/caches
      .keys
      (.then (fn [keys]
               (->> keys
                    (map #(when-not (contains? #{app-cache-name} %)
                            (.delete js/caches %)))
                    clj->js
                    js/Promise.all)))))

(defn- add-cache [request response]
  (-> js/caches
      (.open app-cache-name)
      (.then (fn [cache]
               (.put cache (.-url request) response)))))

(defn- from-cache [request]
  (-> js/caches
      (.match request)
      (.then (fn [response]
               (or response (.reject js/Promise (str "no-match: " (.-url request))))))))

(defn- fetch [request timeout]
  (-> (js/Promise. (fn [fulfill reject]
                     (let [timer (js/setTimeout reject timeout)]
                       (-> (js/fetch request)
                           (.then (fn [response]
                                    (js/clearTimeout timer)
                                    (if (.-ok response)
                                      (add-cache request (.clone response)))
                                    (fulfill response)))
                           (.catch (fn [] (from-cache request)))))))
      (.catch (fn [] (from-cache request)))))

(defn- on-install [e]
  (.log js/console "[ServiceWorker] Installing...")
  (-> js/caches
      (.open app-cache-name)
      (.then (fn [cache]
               (.addAll cache (clj->js files-to-cache))))
      (.then (fn []
               (.skipWaiting js/self)
               (.log js/console "[ServiceWorker] Successfully Installed")))))

(defn- on-fetch [e]
  (let [request (.-request e)]
    (case (:host (parse-url (.-url request)))
      ("noss.al" "nossal.com.br") (fetch request 1400)
      (js/fetch request))))

(defn- on-activate [e]
  (.claim (.-clients js/self))
  (purge-old-cache e)
  (.log js/console "[ServiceWorker] activate"))

(.addEventListener js/self "install" #(.waitUntil % (on-install %)))
(.addEventListener js/self "fetch" #(.respondWith % (on-fetch %)))
(.addEventListener js/self "activate" #(.waitUntil % (on-activate %)))
