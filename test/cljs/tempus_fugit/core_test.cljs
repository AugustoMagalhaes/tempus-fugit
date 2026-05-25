(ns tempus-fugit.core-test
  (:require [cljs.test :refer-macros [is are deftest testing use-fixtures]]
            [pjstadig.humane-test-output]
            [tempus-fugit.core :as rc]))

(deftest test-home
  (is (= true true)))

