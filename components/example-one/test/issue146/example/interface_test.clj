(ns issue146.example.interface-test
  (:require [clojure.test :as test :refer :all]
            [issue146.example.interface :as example]))

(deftest dummy-test
  (is (= 1 1)))
