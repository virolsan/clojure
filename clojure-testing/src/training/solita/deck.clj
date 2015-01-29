(ns training.solita.deck)

(def suit-names
  {\H "heart"
   \C "club"
   \D "diamond"
   \S "spade"})

(defn make-deck []
  (for [suit (keys suit-names)
        value (concat (range 2 11) [\J \Q \K \A])]
    {:suit suit :value value :rank 1}))

(defn get-vals [hand col]
  (reduce #(conj %1 (get %2 col)) [] hand))

(defn has-pair? [hand]
  (apply (complement distinct?) (get-vals hand :value)))

(defn has-straight? [hand]
  (= (reduce - (sort (get-vals hand :rank))) -4))

(defn has-flush? [hand]
  (= (count (set (get-vals hand :suit))) 1))

(defn resolve-better-hand [a b]
  (let [a-points (resolve-points a)
        b-points (resolve-points b)]
  (- a-points b-points)))

(defn resolve-points [hand]
  (let [p1 (if (has-pair? hand) 2 0)
        p2 (if (has-straight? hand) 3 0)
        p3 (if (has-flush? hand) 4 0)]
  (+ p1 p2 p3)))

; testausta

(sort (get-vals (take 3 (make-deck)) :value))
(reduce - (sort > (get-vals (take 3 (make-deck)) :value)))

(make-deck)
(shuffle (make-deck))
(take 5 (make-deck))
(get-vals (take 5 (make-deck)) :rank)

(has-pair? (take 14 (make-deck)))
(has-straight? (take 5 (make-deck)))
(has-flush? (take 5 (make-deck)))

(resolve-points (take 5 (shuffle (make-deck))))
(resolve-better-hand (take 5 (shuffle (make-deck))) (take 2 (make-deck)))

; muita kokeiluja

(filter has-pair (list (take 2 (make-deck)) (take 14 (make-deck))))

(reduce #(conj %1 (get %2 :rank)) [] (take 5 (make-deck)))

(distinct? [1 2 1])
(apply distinct? [1 2 1])
(apply (complement distinct?) [1 2 1])