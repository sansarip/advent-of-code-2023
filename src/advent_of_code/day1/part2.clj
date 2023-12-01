(ns advent-of-code.day1.part2
  (:require [advent-of-code.day1.utils :as utils]
            [clojure.string :as str]))

(def spelled-to-ints
  {"one" 1
   "two" 2
   "three" 3
   "four" 4
   "five" 5
   "six" 6
   "seven" 7
   "eight" 8
   "nine" 9})

(def spelled-numbers-regex #"one|two|three|four|five|six|seven|eight|nine")

(def reverse-spelled-to-ints
  (reduce-kv (fn [m k v] (assoc m (utils/reverse-str k) v)) {} spelled-to-ints))

(def reverse-spelled-numbers-regex #"eno|owt|eerht|ruof|evif|xis|neves|thgie|enin")

(defn replace-first-spelled-num
  ([line] (replace-first-spelled-num line false))
  ([line reverse?]
   (let [[spelled-numbers-regex* spelled-to-ints*] (if reverse? [reverse-spelled-numbers-regex reverse-spelled-to-ints] [spelled-numbers-regex spelled-to-ints])]
     (if-let [string-to-replace (re-find spelled-numbers-regex* line)]
       (str/replace line string-to-replace (str (spelled-to-ints* string-to-replace)))
       line))))

(defn line->first-and-last-numbers [line]
  (let [first-number (some utils/parse-int (replace-first-spelled-num line))
        last-number (some utils/parse-int (replace-first-spelled-num (utils/reverse-str line) :reverse))]
    [first-number last-number]))

(defn parse-input [input]
  (let [lines (str/split input #"\n")]
    (map line->first-and-last-numbers lines)))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        number-tuples (parse-input input)]
    (utils/sum-tuples number-tuples)))

(comment
  (line->first-and-last-numbers "8eight1")
  (line->first-and-last-numbers "eightwothree")
  (replace-first-spelled-num (utils/reverse-str "eightwothree") :reverse)
  (solve-part-2 "src/advent_of_code/day1/input.txt"))