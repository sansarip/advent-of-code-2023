(ns advent-of-code.day4.core
  (:require [advent-of-code.day4.part1 :as part1]
            [advent-of-code.day4.part2 :as part2]))

(def input-path "src/advent_of_code/day4/input.txt")
(def solve-part-1 #(part1/solve-part-1 input-path))
(def solve-part-2 #(part2/solve-part-2 input-path))
