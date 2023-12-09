(ns advent-of-code.day5.part1
  (:require [clojure.string :as str]))

(defn parse-seeds [line]
  (map parse-long (re-seq #"\d+" line)))

(defn parse-section [section]
  (map (comp (fn [[destination-start source-start range]]
               {:source-start source-start
                :destination-start destination-start
                :range range})
             (partial map parse-long)
             reverse
             (partial take 3)
             reverse)
       (re-seq #"((\d+) (\d+) (\d+))" section)))

(defn match-map [source map]
  (some (fn [{:keys [source-start range] :as mapping}]
          (when (and (<= source-start source)
                     (<= source (+ source-start range)))
            mapping))
        map))

(defn source->destination [source {:keys [source-start destination-start]}]
  (+ destination-start (- source source-start)))

(defn parse-input [input]
  (let [[seeds-section & map-sections] (str/split input #"\n\n")]
    (into [(parse-seeds seeds-section)] (map parse-section map-sections))))

(def source->final-destination
  (memoize
   (fn [source maps]
     (reduce (fn [output map]
               (if-let [mapping (match-map output map)]
                 (source->destination output mapping)
                 output))
             source
             maps))))

(defn seeds->min-destination [seeds maps]
  (->> seeds
       (map #(source->final-destination % maps))
       (apply min)))

(defn solve-part-1 [input-path]
  (let [input (slurp input-path)
        [seeds & maps] (parse-input input)]
    (seeds->min-destination seeds maps)))

(comment
  (parse-seeds "seeds: 1778931867 1436999653 3684516104 2759374 1192793053 358764985 1698790056 76369598 3733854793 214008036 4054174000 171202266 3630057255 25954395 798587440 316327323 290129780 7039123 3334326492 246125391")
  (parse-section "seed-to-soil map:\n1965922922 2387203602 59808406\n2540447436 434094583 220346698\n2217992666 1677013102 149631368\n0 700424909 25332775\n2488189883 199146916 52257553\n1096820417 2512808179 247985955\n25332775 725757684 113904366\n4167057552 3534307691 127909744\n1787863383 0 33562512\n2947958449 3662217435 64182733\n2907785302 3360301224 40173147\n3774943096 4218385602 76581694\n693455216 1273647901 403365201\n380961654 1909017232 171283127\n139237141 1031923388 241724513\n2367624034 251404469 38193087\n3038180364 3429533867 104773824\n2484064707 1826644470 4125176\n1344806372 128789319 70357597\n3012141182 3109705711 26039182\n1821425895 289597556 144497027\n598228409 33562512 95226807\n2405817121 1830769646 78247586\n552244781 654441281 45983628\n3641222276 3255639900 104661324\n3851524790 3726400168 315532762\n2025731328 839662050 192261338\n3464769604 4041932930 176452672\n1480960140 2080300359 306903243\n1415163969 2447012008 65796171\n3142954188 2787890295 321815416\n3745883600 3400474371 29059496\n2787890295 3135744893 119895007")
  (parse-input (slurp "src/advent_of_code/day5/input.txt"))
  (->> "src/advent_of_code/day5/input.txt"
       slurp
       parse-input
       second
       (match-map 1965922923))
  (let [mapping (->> "src/advent_of_code/day5/input.txt"
                     slurp
                     parse-input
                     second
                     (match-map 1778931868))]
    (source->destination 1778931868 mapping))
  (source->destination 79 {:source-start 52, :destination-start 50 :range 48})
  (solve-part-1 "src/advent_of_code/day5/input.txt"))