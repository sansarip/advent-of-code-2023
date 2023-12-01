(ns advent-of-code.core
  (:require [advent-of-code.day1.core :as day1]))

(defn print-banner [text]
  (println (str "================================================================================\n"
                text "\n"
                "================================================================================")))

(defn print-solution [day-num & solutions]
  (print-banner (str "Day " day-num " solutions"))
  (let [count (atom 1)]
    (doseq [solution solutions]
      (println "Part" (str @count ":") solution)
      (swap! count inc)))
  (println "\n"))

(def day-1-input-path "src/advent_of_code/day1/input.txt")

(print-solution 1 (day1/solve-part-1 day-1-input-path) (day1/solve-part-2 day-1-input-path))
