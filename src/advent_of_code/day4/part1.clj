(ns advent-of-code.day4.part1
  (:require [clojure.string :as str]
            [clojure.math :as math]))

(def groups-rgx #"((\d+( +|$))+)")

(defn num-str->numbers [num-str]
  (map parse-long (str/split (str/trim num-str) #" +")))

(defn line->lottery-numbers [line]
  (let [[[scratch-nums] [winning-nums]] (re-seq groups-rgx line)]
    {:scratch (num-str->numbers scratch-nums) 
     :winning (num-str->numbers winning-nums)}))

(defn parse-input [input]
  (let [lines (str/split-lines input)]
    (map line->lottery-numbers lines)))

(defn lottery-numbers->num-wins [{:keys [scratch winning]}]
  (count (filter #(some #{%} winning) scratch)))

(defn win->result [win]
  (int (if (<= win 1)
         win
         (math/pow 2 (dec win)))))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        lottery-numbers (parse-input input)
        wins-per-game (map lottery-numbers->num-wins lottery-numbers)
        results (map win->result wins-per-game)]
    (apply + results)))

(comment
  (num-str->numbers "59 65 20 66 55 92 43 23 98 70 ")
  (line->lottery-numbers "Card   1: 59 65 20 66 55 92 43 23 98 70 | 99 81 56 30 88 55 57 11 90 45 53 28 33 20 84 54 24 64 74 98 36 77 61 82 69")
  (line->lottery-numbers "Card 198: 87  3 64 10 88 45 16 40 23 60 | 63 77 36 52 47 76 84 96 19 13 73 39 26 93 21 22  7 15 95 30 33 89 28 20 50")
  (win->result 0)
  (solve-part-1 "src/advent_of_code/day4/input.txt"))