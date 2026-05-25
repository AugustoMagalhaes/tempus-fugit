(ns tempus-fugit.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub :current-page (fn [db _] (:current-page db)))
(rf/reg-sub :user          (fn [db _] (:user db)))
(rf/reg-sub :loading?      (fn [db _] (:loading? db)))
(rf/reg-sub :error         (fn [db _] (:error db)))