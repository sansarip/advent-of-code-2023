(ns advent-of-code.day4.part2
  (:require [advent-of-code.day4.part1 :as d4p1]))

(defn replace-vec [v replacement start end]
  (let [replacement (vec replacement)]
    (vec (concat (subvec v 0 start)
                 replacement
                 (subvec v end)))))

(defn copy-cards [wins-per-game]
  (loop [idx 0
         num-cards-vec (into [] (repeat (count wins-per-game) 1))]
    (let [wins (get wins-per-game idx)
          current-num-copies (get num-cards-vec idx)
          next-idx (inc idx)
          next-end (+ next-idx wins)]
      (cond
        (= next-idx (count wins-per-game)) num-cards-vec 
        (zero? wins) (recur next-idx num-cards-vec)
        :else (recur next-idx 
                     (replace-vec 
                      num-cards-vec
                      (map #(+ % current-num-copies) (subvec num-cards-vec next-idx next-end))
                      next-idx
                      next-end))))))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        lottery-numbers (d4p1/parse-input input)
        wins-per-game (into [] (map d4p1/lottery-numbers->num-wins lottery-numbers))]
    (apply + (copy-cards wins-per-game))))

(comment
  (subvec [1 2 3 4 5 6] (inc 2) (inc (+ 2 3))) 
  (replace-vec [0 1 2 3 4] [2 3 4] 1 4) 
  (solve-part-2 "src/advent_of_code/day4/input.txt"))