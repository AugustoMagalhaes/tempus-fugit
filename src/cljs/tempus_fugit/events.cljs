(ns tempus-fugit.events
  (:require [re-frame.core :as rf]
            [tempus-fugit.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 :set-current-page
 (fn [db [_ page]]
   (assoc db :current-page page)))

(rf/reg-event-db
 :set-user
 (fn [db [_ user]]
   (assoc db :user user)))