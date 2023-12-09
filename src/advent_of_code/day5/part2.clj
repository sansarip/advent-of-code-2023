(ns advent-of-code.day5.part2
  (:require [advent-of-code.day5.part1 :as d5p1]))

(defn parse-input [input]
  (let [[seeds & maps] (d5p1/parse-input input)
        seed-ranges (map (fn [[start length]] 
                           [start (+ start length)])
                         (partition 2 seeds))]
    (into [seed-ranges] maps)))


(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        [seed-ranges & maps] (parse-input input)
        min-destinations (pmap (comp #(d5p1/seeds->min-destination % maps) #(apply range %)) seed-ranges)]
    (apply min min-destinations)))

(comment
  (partition 2 [1 2 3 4])
  (range 1778931867 (+ 1778931867 1436999653))
  (solve-part-2 "src/advent_of_code/day5/input.txt"))