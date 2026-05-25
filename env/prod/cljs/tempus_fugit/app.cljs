(ns tempus-fugit.app
  (:require [tempus-fugit.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
