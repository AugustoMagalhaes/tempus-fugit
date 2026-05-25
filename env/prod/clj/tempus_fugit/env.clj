(ns tempus-fugit.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[tempus-fugit started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[tempus-fugit has shut down successfully]=-"))
   :middleware identity})
