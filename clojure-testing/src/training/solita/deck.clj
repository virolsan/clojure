(ns training.solita.deck)

(def suit-names
  {\H "heart"
   \C "club"
   \D "diamond"
   \S "spade"})
(def deck-values
  (into [] (concat (range 2 11) [\J \Q \K \A])))

(defn make-deck []
  (for [suit (keys suit-names)
        value deck-values]
    {:suit suit :value value :rank (+ 2 (.indexOf deck-values value))}))

(defn get-vals [hand col]
  (reduce #(conj %1 (get %2 col)) [] hand))

(defn has-pair? [hand]
  (apply (complement distinct?) (get-vals hand :value)))

(defn has-straight? [hand]
  (= (reduce - (sort (get-vals hand :rank))) -4))

(defn has-flush? [hand]
  (= (count (set (get-vals hand :suit))) 1))

(defn resolve-points [hand]
  (let [p1 (if (has-pair? hand) 2 0)
        p2 (if (has-straight? hand) 3 0)
        p3 (if (has-flush? hand) 4 0)]
  (println (get-vals hand :value) ": " (+ p1 p2 p3) "pts")
  (+ p1 p2 p3)))

(defn resolve-better-hand [a b]
  (let [a-points (resolve-points a)
        b-points (resolve-points b)]
  (if (> a-points b-points) a b)))


(defn deal-cards[players]
  (let [deck (shuffle (make-deck))]
    (take players (partition 5 deck))))

(let [game (deal-cards 3)]
  (reduce resolve-better-hand game))



; testausta

(make-deck)
(deal-cards 1)

(has-pair? (nth (deal-cards 1) 0))
(has-straight? (nth (deal-cards 1) 0))
(has-flush? (nth (deal-cards 1) 0))

(resolve-points (nth (deal-cards 1) 0))
(reduce resolve-better-hand (deal-cards 2))



; muita kokeiluja

(filter has-pair? (list (take 2 (make-deck)) (take 14 (make-deck))))

(reduce #(conj %1 (get %2 :rank)) [] (take 5 (make-deck)))

(distinct? [1 2 1])
(apply distinct? [1 2 1])
(apply (complement distinct?) [1 2 1])

(take 13 (iterate inc 2))