(ns advent-of-code.day2.part2
  (:require [advent-of-code.day2.part1 :as d1p1]))

(defn games->product-of-maximums [games]
  (map (fn [{:keys [max-red max-blue max-green]}]
         (apply * (map #(max % 1) [max-red max-blue max-green])))
       games))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path) 
        games (d1p1/parse-input input)
        products (games->product-of-maximums games)] 
    (apply + products)))

(comment
  (games->product-of-maximums [{:max-red 3 :max-blue 7 :max-green 10}
                               {:max-red 4 :max-blue 4 :max-green 0}
                               {:max-red 5 :max-blue 7 :max-green 1}]) 
  (solve-part-2 "src/advent_of_code/day2/input.txt"))