(ns tempus-fugit.core
  (:require [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [tempus-fugit.events]
            [tempus-fugit.views.index :as views]))

(defn mount-components []
  (rf/clear-subscription-cache!)
  (rdom/render [views/main-panel] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:initialize-db])
  (mount-components))