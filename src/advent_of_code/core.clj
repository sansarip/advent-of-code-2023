(ns advent-of-code.core
  (:require [advent-of-code.day1.core :as day1]
            [advent-of-code.day2.core :as day2]
            [advent-of-code.day3.core :as day3]
            [advent-of-code.day4.core :as day4]))

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


(print-solution 1 (day1/solve-part-1) (day1/solve-part-2))
(print-solution 2 (day2/solve-part-1) (day2/solve-part-2))
(print-solution 3 (day3/solve-part-1) (day3/solve-part-2))
(print-solution 4 (day4/solve-part-1) (day4/solve-part-2))