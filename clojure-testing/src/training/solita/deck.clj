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

(defn partition-hand [hand key]
   (partition-by identity (sort (get-vals hand key))))

(defn group-hand-by-count [hand key]
   (group-by count (partition-hand hand key)))

(defn max-kind [hand]
  (apply max (keys (group-hand-by-count hand :rank))))

(defn high-card [hand]
  (first (reverse (sort (get-vals hand :rank)))))

(defn has-pair? [hand]
  (= 2 (max-kind hand)))

(defn has-three-of-a-kind? [hand]
  (= 3 (max-kind hand)))

(defn has-four-of-a-kind? [hand]
  (= 4 (max-kind hand)))

(defn has-two-pairs? [hand]
  (= 2 (count (filter #(> (count %) 1) (partition-hand hand :rank)))))

(defn has-straight? [hand]
  (let [ranks (sort (get-vals hand :rank))]
    (and (apply distinct? ranks)
         (= 4 (- (nth ranks 4) (nth ranks 0))))))

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
  (cond
    (and (has-flush? hand) 
         (has-straight? hand)
         (= 14 (high-card hand))) 10
    (and (has-flush? hand) 
         (has-straight? hand))    9
    (has-four-of-a-kind? hand)    8
    (and (has-three-of-a-kind? hand) 
         (has-two-pairs? hand ))  7
    (has-flush? hand)             6
    (has-straight? hand)          5
    (has-three-of-a-kind? hand)   4
    (has-two-pairs? hand)         3
    (has-pair? hand)              2
    :else                         0))

(defn resolve-better-hand [a b]
  (let [a-points (resolve-points a)
        b-points (resolve-points b)]
    (cond 
      (> a-points b-points) a
      (> b-points a-points) b
      (> (high-card a) (high-card b)) a
      (> (high-card b) (high-card a)) b
      :else nil)))


(let [hands (deal-cards 5 3)]
  (reduce resolve-better-hand hands))



; testausta

(make-deck)
(deal-cards 5 1)

(keys (group-hand-by-count (nth (deal-cards 5 1) 0) :rank))
(count (group-hand-by-count (nth (deal-cards 5 1) 0) :rank))

(resolve-points (nth (deal-cards 5 1) 0))
(reduce resolve-better-hand (deal-cards 5 2))


; muita kokeiluja

(reduce #(conj %1 (get %2 :rank)) [] (take 5 (make-deck)))

(distinct? [1 2 1])
(apply distinct? [1 2 1])
(apply (complement distinct?) [1 2 1])

(take 13 (iterate inc 2))