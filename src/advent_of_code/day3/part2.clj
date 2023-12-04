(ns advent-of-code.day3.part2
  (:require [advent-of-code.day3.part1 :as d3p1]))

(defn adjacent [match1 match2]
  (when (d3p1/adjacent? match1 match2)
    match2))

(defn gear-part-ratios [symbol-match numbers]
  (when-let [adj-num1 (some #(adjacent symbol-match %) numbers)]
    (let [numbers-without-adj-num-1 (remove #(= adj-num1 %) numbers)
          adj-num2 (some #(adjacent symbol-match %) numbers-without-adj-num-1)]
      (when adj-num2
        (* (-> adj-num1 :match parse-long)
           (-> adj-num2 :match parse-long))))))

(defn solve-part-2 [input-path]
  (let [input (slurp input-path)
        {:keys [symbols numbers]} (d3p1/parse-input input)
        gear-part-ratios (into []
                               (comp
                                (filter (comp #(= "*" %) :match))
                                (map #(gear-part-ratios % numbers))
                                (filter identity))
                               symbols)]
    (apply + gear-part-ratios)))

(comment
  (gear-part-ratios {:relative-start-offset 46,
                     :relative-end-offset 47,
                     :start-offset 46,
                     :end-offset 47,
                     :match "*",
                     :start-coords {:x 46, :y 1},
                     :end-coords {:x 47, :y 1},
                     :kind :symbol}
                    [{:relative-start-offset 44,
                      :relative-end-offset 47,
                      :start-offset 44,
                      :end-offset 47,
                      :match "411",
                      :start-coords {:x 44, :y 0},
                      :end-coords {:x 47, :y 0},
                      :kind :number}
                     {:relative-start-offset 45,
                      :relative-end-offset 48,
                      :start-offset 325,
                      :end-offset 328,
                      :match "855",
                      :start-coords {:x 45, :y 2},
                      :end-coords {:x 48, :y 2},
                      :kind :number}])
  (solve-part-2 "src/advent_of_code/day3/input.txt"))
