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
  ((complement distinct?) (get-vals hand :rank)))


(make-deck)

(shuffle (make-deck))

(has-pair (take 5 (make-deck)))

; kokeiluja

(reduce #(conj %1 (get %2 :rank)) [] (take 5 (make-deck)))
