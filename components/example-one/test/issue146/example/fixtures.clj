(ns issue146.example.fixtures
  (:require [cheshire.core]))

(defn pre-test [& _] (println "example-one with cheshire"))