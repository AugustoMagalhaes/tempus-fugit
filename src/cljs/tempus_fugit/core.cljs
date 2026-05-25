(ns tempus-fugit.core
  (:require [reagent.dom.client :as rdom]
            [re-frame.core :as rf]
            [tempus-fugit.events]
            [tempus-fugit.subs]
            [tempus-fugit.views.index :as views]))

(defonce root (atom nil))

(defn mount-components []
  (rf/clear-subscription-cache!)
  (when-not @root
    (reset! root (rdom/create-root (.getElementById js/document "app"))))
  (rdom/render @root [views/main-panel]))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (mount-components))