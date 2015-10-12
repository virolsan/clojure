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

(defn deal-cards[cards players]
  (let [deck (shuffle (make-deck))]
    (take players (partition cards deck))))

(defn get-vals [hand col]
  (reduce #(conj %1 (get %2 col)) [] hand))

(defn group-hand-by-count [hand key]
   (group-by count (partition-by identity (sort (get-vals hand key)))))

(defn max-kind [hand]
  (apply max (keys (group-hand-by-count hand :rank))))

(defn has-two-pairs? [hand]
  (= 2 (count ((group-hand-by-count hand :rank) 2))))

(defn has-straight? [hand]
  (let [ranks (sort (get-vals hand :rank))]
    (= 4 (- (nth ranks 4) (nth ranks 0)))))

(defn has-flush? [hand]
  (not (empty? ((group-hand-by-count hand :suit) 5))))

; Ranking:
;  1 = royal flush     10 pts
;  2 = straight flush   9 pts
;  3 = four of a kind   8 pts
;  4 = full house       7 pts
;  5 = flush            6 pts
;  6 = straight         5 pts
;  7 = three of a kind  4 pts
;  8 = two pairs        3 pts
;  9 = one pair         2 pts
; 10 = high card
(defn resolve-points [hand]
  (max
  (if (= 2 (max-kind hand)) 2 0)
  (if (has-two-pairs? hand) 3 0)
  (if (= 3 (max-kind hand)) 4 0)
  (if (has-straight? hand)  5 0)
  (if (has-flush? hand)     6 0)
  (if (= 4 (max-kind hand)) 8 0)))

(defn resolve-better-hand [a b]
  (let [a-points (resolve-points a)
        b-points (resolve-points b)]
  (if (> a-points b-points) a b)))


(let [hands (deal-cards 5 3)]
  (reduce resolve-better-hand hands))



; testausta

(make-deck)
(deal-cards 5 1)

(group-hand-by-count (nth (deal-cards 5 1) 0) :rank)

(has-straight? '( {:suit \D, :value 4, :rank 4}
                  {:suit \C, :value 3, :rank 3}
                  {:suit \C, :value 6, :rank 6}
                  {:suit \C, :value 5, :rank 5}
                  {:suit \S, :value 7, :rank 7}))
(has-flush? '( {:suit \C, :value 4, :rank 4}
               {:suit \C, :value \A, :rank 14}
               {:suit \C, :value 3, :rank 3}
               {:suit \C, :value 6, :rank 6}
               {:suit \C, :value 7, :rank 7}))
(has-two-pairs? '( {:suit \D, :value 4, :rank 4}
                   {:suit \C, :value \A, :rank 14}
                   {:suit \C, :value 6, :rank 6}
                   {:suit \C, :value 6, :rank 6}
                   {:suit \S, :value 4, :rank 4}))

(resolve-points (nth (deal-cards 5 1) 0))
(reduce resolve-better-hand (deal-cards 5 2))



; muita kokeiluja

(filter has-pair? (list (take 2 (make-deck)) (take 14 (make-deck))))

(reduce #(conj %1 (get %2 :rank)) [] (take 5 (make-deck)))

(distinct? [1 2 1])
(apply distinct? [1 2 1])
(apply (complement distinct?) [1 2 1])

(take 13 (iterate inc 2))