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

;(defn has-pair [hand]
;  (into [] (apply (-> hand :rank) hand)))

(make-deck)

(shuffle (make-deck))

;(has-pair (take 5 (make-deck)))

(take 5 (shuffle (make-deck)))

(apply str (take 5 (make-deck)))
(fn [hand] (into [] (apply (str (:rank hand)))) (take 5 (make-deck)))