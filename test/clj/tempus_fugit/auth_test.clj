(ns tempus-fugit.auth-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [tempus-fugit.handler :refer [app]]
   [tempus-fugit.db.core :refer [*db*] :as db]
   [luminus-migrations.core :as migrations]
   [tempus-fugit.config :refer [env]]
   [buddy.hashers :as hashers]
   [cheshire.core :as json]
   [mount.core :as mount]))

(def test-user
  {:id         (java.util.UUID/randomUUID)
   :email      "test@tempusfugit.com"
   :password   (hashers/derive "testpass123")
   :first_name "Test"
   :last_name  "User"
   :role       "employee"
   :manager_id nil
   :is_active  true})

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'tempus-fugit.config/env
     #'tempus-fugit.db.core/*db*
     #'tempus-fugit.handler/app-routes)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (db/delete-user-by-email! {:email (:email test-user)})
    (f)
    (db/delete-user-by-email! {:email (:email test-user)})))

(defn parse-body [response]
  (try
    (json/parse-string (slurp (:body response)) true)
    (catch Exception _ nil)))

(deftest test-login
  (db/create-user! {:id         (:id test-user)
                    :email      (:email test-user)
                    :password   (:password test-user)
                    :first_name (:first_name test-user)
                    :last_name  (:last_name test-user)
                    :role       (:role test-user)
                    :manager_id (:manager_id test-user)
                    :is_active  (:is_active test-user)})

  (testing "login com credenciais corretas"
    (let [response ((app)
                    (-> (mock/request :post "/api/auth/login")
                        (mock/json-body {:email "test@tempusfugit.com"
                                         :password "testpass123"})))
          body (parse-body response)]
      (is (= 200 (:status response)))
      (is (= "test@tempusfugit.com" (get-in body [:user :email])))
      (is (nil? (get-in body [:user :password])))))

  (testing "login com senha errada"
    (let [response ((app)
                    (-> (mock/request :post "/api/auth/login")
                        (mock/json-body {:email "test@tempusfugit.com"
                                         :password "senhaerrada"})))
          body (parse-body response)]
      (is (= 401 (:status response)))
      (is (= "Invalid email or password" (:message body)))))

  (testing "login com email inexistente"
    (let [response ((app)
                    (-> (mock/request :post "/api/auth/login")
                        (mock/json-body {:email "naoexiste@tempusfugit.com"
                                         :password "testpass123"})))
          body (parse-body response)]
      (is (= 401 (:status response))))))

(deftest test-me-sem-auth
  (testing "/me sem sessão retorna 401"
    (let [response ((app)
                    (mock/request :get "/api/auth/me"))
          body (parse-body response)]
      (is (= 401 (:status response))))))