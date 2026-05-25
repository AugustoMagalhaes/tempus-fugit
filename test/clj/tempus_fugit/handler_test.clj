(ns tempus-fugit.handler-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [tempus-fugit.handler :refer [app]]
   [tempus-fugit.config :refer [env]]
   [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
     #'tempus-fugit.config/env
     #'tempus-fugit.handler/app-routes)
    (f)
    (mount/stop
     #'tempus-fugit.handler/app-routes)))

(deftest test-main-routes
  (testing "home route returns 200"
    (let [response ((app) (mock/request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "inexistent route returns 404"
    (let [response ((app) (mock/request :get "/inexistent-route123"))]
      (is (= 404 (:status response)))))

  (testing "/api/auth/me without session returns 401"
    (let [response ((app) (mock/request :get "/api/auth/me"))]
      (is (= 401 (:status response))))))