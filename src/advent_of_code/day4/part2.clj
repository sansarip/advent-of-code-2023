(ns advent-of-code.day4.part2
  (:require [advent-of-code.day4.part1 :as d4p1]))

(defn replace-vec [v replacement start end]
  (let [replacement (vec replacement)]
    (vec (concat (subvec v 0 start)
                 replacement
                 (subvec v end)))))

(defn copy-cards [wins-per-game]
  (loop [idx 0
         card-copies (into [] (repeat (count wins-per-game) 1))]
    (let [current-wins (get wins-per-game idx)
          current-num-copies (get card-copies idx)
          next-idx (inc idx)
          next-end (+ next-idx current-wins)]
      (cond
        (= next-idx (count wins-per-game)) card-copies 
        (zero? current-wins) (recur next-idx card-copies)
        :else (recur next-idx 
                     (replace-vec 
                      card-copies
                      (map #(+ % current-num-copies) (subvec card-copies next-idx next-end))
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