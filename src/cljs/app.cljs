(ns cljs.app

 (:require-macros [cljs.core.async.macros :refer [go]])
 (:require [goog.events :as events]
           [cljs.core.async :refer [<! chan]]
           [om.core :as om :include-macros true]
           [om.dom :as dom :include-macros true]
           [secretary.core :as secretary]
           [clojure.string :as string])
 (:import [goog History]
          [goog.history EventType]))

(js/alert "Hello from ClojureScript!!!")
