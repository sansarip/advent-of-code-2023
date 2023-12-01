(ns advent-of-code.day1.utils
  (:require [clojure.string :as str]))

(defn reverse-str [s]
  (apply str (reverse s)))

(defn parse-int [c]
  (try (Integer/parseInt (str c))
       (catch Exception _)))

(defn sum-tuples [tuples]
  (apply + (map (comp parse-int str/join) tuples)))