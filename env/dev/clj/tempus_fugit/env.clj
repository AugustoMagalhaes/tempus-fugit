(ns tempus-fugit.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [tempus-fugit.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[tempus-fugit started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[tempus-fugit has shut down successfully]=-"))
   :middleware wrap-dev})
