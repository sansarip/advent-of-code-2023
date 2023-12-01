(ns advent-of-code.day1.part1
  (:require [clojure.string :as str]
            [advent-of-code.day1.utils :as utils]))

(defn line->first-and-last-numbers [line]
  (let [first-number (some utils/parse-int line)
        last-number (some utils/parse-int (utils/reverse-str line))]
    [first-number last-number]))

(defn parse-input [input]
  (let [lines (str/split input #"\n")]
    (map line->first-and-last-numbers lines)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        number-tuples (parse-input input)]
    (utils/sum-tuples number-tuples)))

(comment
  (solve-part-1 "src/advent_of_code/day1/input.txt"))