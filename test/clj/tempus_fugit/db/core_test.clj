(ns tempus-fugit.db.core-test
  (:require
   [tempus-fugit.db.core :refer [*db*] :as db]
   [java-time.pre-java8]
   [luminus-migrations.core :as migrations]
   [clojure.test :refer :all]
   [next.jdbc :as jdbc]
   [tempus-fugit.config :refer [env]]
   [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'tempus-fugit.config/env
     #'tempus-fugit.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-transaction [t-conn *db* {:rollback-only true}]
    (let [user-id (java.util.UUID/randomUUID)]
      (is (= 1 (db/create-user!
                t-conn
                {:id         user-id
                 :email      "sam.smith@example.com"
                 :password   "pass"
                 :first_name "Sam"
                 :last_name  "Smith"
                 :role       "employee"
                 :manager_id nil
                 :is_active  true}
                {})))
      (is (= "sam.smith@example.com"
             (:email (db/get-user t-conn {:email "sam.smith@example.com"} {})))))))