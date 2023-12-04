(ns advent-of-code.day3.part1
  (:require [clojure.string :as str]))

(def rgx #"\d+|[-+*$&=@/#%_]+")

(defn match->kind [match]
  (if (re-find #"\d+" match)
    :number
    :symbol))

(defn pos-matches
  ([pattern s]
   (pos-matches pattern s 0 0))
  ([pattern s x-offset y-offset]
   (let [matcher (re-matcher pattern s)]
     (loop [matches []
            start 0]
       (if (.find matcher start)
         (let [match (.group matcher)
               start (.start matcher)
               end (.end matcher)]
           (recur (conj matches {:relative-start-offset start
                                 :relative-end-offset end
                                 :start-offset (+ start x-offset)
                                 :end-offset (+ end x-offset)
                                 :match match
                                 :start-coords {:x start :y y-offset}
                                 :end-coords {:x end :y y-offset}
                                 :kind (match->kind match)})
                  (.end matcher)))
         matches)))))

(defn overlaps? [[range1-start range1-end] [range2-start range2-end]]
  (or (<= range2-start
          range1-start
          range1-end
          range2-end)
      (<= range1-start
          range2-start
          range2-end
          range1-end)))

(defn adjacent? [match1 match2]
  (let [m1-relative-start-offset (:relative-start-offset match1)
        m1-relative-end-offset (:relative-end-offset match1)
        m2-relative-start-offset (:relative-start-offset match2)
        m2-relative-end-offset (:relative-end-offset match2)
        m1-start-y (get-in match1 [:start-coords :y])
        m2-start-y (get-in match2 [:start-coords :y])
        within-1-row? (<= (abs (- m1-start-y m2-start-y)) 1)
        horizontally-adjacent? (or (= m2-relative-start-offset m1-relative-end-offset)
                                   (= m2-relative-end-offset m1-relative-start-offset))
        overlaps? (overlaps? [m2-relative-start-offset m2-relative-end-offset]
                             [m1-relative-start-offset m1-relative-end-offset])]
    (and within-1-row? (or horizontally-adjacent? overlaps?))))

(defn part? [number-match symbols]
  (some #(adjacent? number-match %) symbols))

(defn line->sym-and-num [line row num-columns]
  (let [match-positions (pos-matches rgx line (* row num-columns) row)]
    {:numbers (filter #(= :number (:kind %)) match-positions)
     :symbols (filter #(= :symbol (:kind %)) match-positions)}))

(defn parse-input [input]
  (let [lines (str/split-lines input)
        num-columns (count (first lines))
        sym-and-num-vec (map-indexed
                         (fn [row line]
                           (line->sym-and-num line row num-columns))
                         lines)]
    (reduce (fn aggr [c match]
              (-> c
                  (update :symbols into (:symbols match))
                  (update :numbers into (:numbers match))))
            {:symbols [] :numbers []}
            sym-and-num-vec)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        {:keys [symbols numbers]} (parse-input input)
        part-nums (into []
                        (comp
                         (filter #(part? % symbols))
                         (map (comp parse-long :match)))
                        numbers)]
    (apply + part-nums)))

(comment
  [[0, 0], [1, 0], [2, 0]
   [0, 1], [1, 1], [2, 1]
   [0, 2], [1, 2], [2, 2]]
  (adjacent?
   {:relative-start-offset 44,
    :relative-end-offset 47,
    :start-offset 44,
    :end-offset 47,
    :match "411",
    :start-coords {:x 44, :y 0},
    :end-coords {:x 47, :y 0},
    :kind :number}
   {:relative-start-offset 46,
    :relative-end-offset 47,
    :start-offset 46,
    :end-offset 47,
    :match "*",
    :start-coords {:x 46, :y 1},
    :end-coords {:x 47, :y 1},
    :kind :symbol})
  (adjacent?
   {:relative-start-offset 85,
    :relative-end-offset 88,
    :start-offset 85,
    :end-offset 88,
    :match "463",
    :start-coords {:x 85, :y 0},
    :end-coords {:x 88, :y 0},
    :kind :number}
   {:relative-start-offset 46,
    :relative-end-offset 47,
    :start-offset 46,
    :end-offset 47,
    :match "*",
    :start-coords {:x 46, :y 1},
    :end-coords {:x 47, :y 1},
    :kind :symbol})
  (adjacent?
   {:relative-start-offset 68,
    :relative-end-offset 71,
    :start-offset 68,
    :end-offset 71,
    :match "363",
    :start-coords {:x 68, :y 0},
    :end-coords {:x 71, :y 0},
    :kind :number}
   {:relative-start-offset 71,
    :relative-end-offset 72,
    :start-offset 71,
    :end-offset 72,
    :match "*",
    :start-coords {:x 71, :y 1},
    :end-coords {:x 72, :y 1},
    :kind :symbol})
  (re-find rgx "-+*$&=@/#%_")
  (overlaps? [68 71] [71 72])
  (<= 68 71 71 72)
  (pos-matches rgx "............................................411.....................363..134.........463.775..........................506...................")
  (pos-matches rgx "......429...836..$............../..960........*.............+..........*...=....381.....*........67......426.....=..../...304...............")
  (pos-matches rgx ".........*...&...641..........924..*.........855....492..495.......476.927.......*.........680...../.&....*.....713......*.................." 280 2)
  (pos-matches rgx "...........*717.868...+.............252....................................*......461.........*......................350....................")
  (solve-part-1 "src/advent_of_code/day3/input.txt"))
