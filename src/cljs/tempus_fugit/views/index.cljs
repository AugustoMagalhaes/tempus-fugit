(ns tempus-fugit.views.index
  (:require [re-frame.core :as rf]))

(defn main-panel []
  (let [page @(rf/subscribe [:current-page])]
    [:div.container
     [:h1 "Tempus Fugit"]
     [:p (str "Page: " page)]]))