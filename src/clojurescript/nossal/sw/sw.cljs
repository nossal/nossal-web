(ns nossal.sw)


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
               (or response (js/Promise.reject (str "no-match: " (.-url request))))))))

(defn- fetch [request]
  (-> (js/fetch request)
      (.then (fn [response]
               (if (.-ok response)
                 (add-cache request (.clone response)))
               response))
      (.catch (fn [] (from-cache request)))))

(defn- on-install [e]
  (js/console.log "[ServiceWorker] Installing...")
  (-> js/caches
      (.open app-cache-name)
      (.then (fn [cache]
               (.addAll cache (clj->js files-to-cache))))
      (.then (fn []
               (.skipWaiting js/self)
               (js/console.log "[ServiceWorker] Successfully Installed")))))

(defn- on-fetch [e]
  (let [request (.-request e)]
    (case (:host (parse-url (.-url request)))
      ("localhost" "noss.al" "nossal.com.br") (fetch request)
      (js/fetch request))))

(defn- on-activate [e]
  (.claim (.-clients js/self))
  (purge-old-cache e)
  (js/console.log "[ServiceWorker] activate"))


(.addEventListener js/self "install" #(.waitUntil % (on-install %)))
(.addEventListener js/self "fetch" #(.respondWith % (on-fetch %)))
(.addEventListener js/self "activate" #(.waitUntil % (on-activate %)))
