(ns tempus-fugit.routes.auth
  (:require
   [tempus-fugit.db.core :as db]
   [tempus-fugit.middleware :as middleware]
   [buddy.hashers :as hashers]
   [ring.util.http-response :as response]
   [clojure.tools.logging :as log]))

(defn login! [{:keys [params session]}]
  (let [{:keys [email password]} params
        user (db/get-user-by-email {:email email})]
    (if (and user (hashers/check password (:password user)))
      (-> (response/ok {:user (dissoc user :password)})
          (assoc :session (assoc session :identity (dissoc user :password))))
      (response/unauthorized {:message "Invalid email or password"}))))

(defn logout! [_]
  (-> (response/ok {:message "Logged out"})
      (assoc :session nil)))

(defn me [{{:keys [identity]} :session}]
  (if identity
    (response/ok {:user identity})
    (response/unauthorized {:message "Not authenticated"})))

(defn auth-routes []
  ["/api/auth"
   {:middleware [middleware/wrap-formats]}
   ["/login"  {:post login!}]
   ["/logout" {:post logout!}]
   ["/me"     {:get  me}]])