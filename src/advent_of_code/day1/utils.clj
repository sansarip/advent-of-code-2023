(ns advent-of-code.day1.utils)

(defn reverse-str [s]
  (apply str (reverse s)))

(defn parse-int [c]
  (try (Integer/parseInt (str c))
       (catch Exception _)))

(defn sum-tuples [str-nums]
  (apply + (map parse-int str-nums)))