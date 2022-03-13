(ns nossal.state.global
  (:require [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [nossal.state.navigation]
            [nossal.state.user-info :as user-info]))


(rf/reg-event-fx :initialize
  (fn [_db _event]
    {:db         (merge user-info/initial-db)
     :http-xhrio (vector user-info/initial-http)}))


(rf/reg-event-db :println
  (fn [db [_ path]]
    (println (get-in db path))
    db))
