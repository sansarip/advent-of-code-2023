(ns advent-of-code.day2.part1
  (:require [clojure.string :as str]))

(defn find-and-parse-int [s]
  (let [num (re-find #"\d+" s)]
    (try (Integer/parseInt num)
         (catch Exception _))))

(defn num-color-str->num-color-map [s]
  (let [[_ num color] (re-matches #"\s?(\d+) (\w+)\s?" s)]
    (try {:num (Integer/parseInt num)
          :color color}
         (catch Exception _))))

(defn num-color-strs->num-color-maps [num-color-strs]
  (map num-color-str->num-color-map num-color-strs))

(defn round-strs->round-info-maps [round-strs]
  (mapcat (comp num-color-strs->num-color-maps #(str/split % #",")) round-strs))

(defn line->game-info [line]
  (let [[game-str rounds-str] (str/split line #":")
        game-id (find-and-parse-int game-str)
        round-info-maps (round-strs->round-info-maps (str/split rounds-str #";"))]
    (reduce (fn aggr-colors [c {:keys [num color]}]
              (update c (keyword (str "max-" color)) max num)) 
            {:game-id game-id 
             :max-red 0 
             :max-blue 0 
             :max-green 0} 
            round-info-maps)))

(defn parse-input [input]
  (let [lines (str/split-lines input)] 
    (map line->game-info lines)))

(defn filter-possible-games [games {total-red :red total-blue :blue total-green :green}]
  (filter (fn [{:keys [max-red max-blue max-green]}]
            (and (<= max-red total-red)
                 (<= max-blue total-blue)
                 (<= max-green total-green)))
          games))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        games (parse-input input)
        totals-in-bag {:red 12 :blue 14 :green 13}
        possible-games (filter-possible-games games totals-in-bag)]
    (reduce (fn sum-ids [aggr {:keys [game-id]}] (+ aggr game-id)) 0 possible-games)))

(comment
  (str/split  "Game 1: 3 blue, 7 green, 10 red; 4 green, 4 red; 1 green, 7 blue, 5 red; 8 blue, 10 red; 7 blue, 19 red, 1 green" #":")
  (str/split " 3 blue, 7 green, 10 red; 4 green, 4 red; 1 green, 7 blue, 5 red; 8 blue, 10 red; 7 blue, 19 red, 1 green" #";")
  (str/split "3 blue, 7 green, 10 red" #",")
  (re-matches #"^\s?(\d+) (\w+)\s?$" " 3 blue ")
  (map num-color-str->num-color-map ["3 blue" " 7 green" " 10 red"])
  (round-strs->round-info-maps [" 3 blue, 7 green, 10 red" " 4 green, 4 red" " 1 green, 7 blue, 5 red" " 8 blue, 10 red" " 7 blue, 19 red, 1 green"])
  (line->game-info "Game 1: 3 blue, 7 green, 10 red; 4 green, 4 red; 1 green, 7 blue, 5 red; 8 blue, 10 red; 7 blue, 19 red, 1 green" )
  (line->game-info "Game 80: 8 red, 5 green, 7 blue; 15 red, 6 blue, 8 green; 8 blue, 14 red, 7 green; 3 blue, 2 green, 20 red; 10 red, 8 blue, 1 green; 7 green, 11 red, 9 blue")
  (solve-part-1 "src/advent_of_code/day2/input.txt"))