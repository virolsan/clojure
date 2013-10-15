(ns training.solita.deck)

(def suit-names
  {\H "heart"
   \C "club"
   \D "diamond"
   \S "spade"})

(defn make-deck []
  (for [suit (keys suit-names)
        rank (concat (range 2 11) [\J \Q \K \A])]
    {:suit suit :rank rank}))

(defn get-vals [hand col]
  (reduce #(conj %1 (get %2 :rank)) [] hand))

(defn has-pair [hand]
  (apply (complement distinct?) (get-vals hand :rank)))



; testausta

(make-deck)

(shuffle (make-deck))

(get-vals (take 5 (make-deck)) :rank)
(get-vals (take 14 (make-deck)) :rank)

(has-pair (take 5 (make-deck)))
(has-pair (take 14 (make-deck)))


; muita kokeiluja

(reduce #(conj %1 (get %2 :rank)) [] (take 5 (make-deck)))

(distinct? [1 1])
(apply distinct? [1 1])