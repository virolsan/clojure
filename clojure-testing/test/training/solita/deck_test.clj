(ns training.solita.deck-test
  (:use [training.solita.deck]
        [midje.sweet]))

(facts "General"
  (fact "Size of deck" 
        (count (make-deck)) => 52)
)

(facts "Resolve hands"
  (fact "Single pair"
        (has-pair? '( {:suit \D, :value 4, :rank 4}
                      {:suit \C, :value \A, :rank 14}
                      {:suit \C, :value 6, :rank 6}
                      {:suit \C, :value 5, :rank 5}
                      {:suit \S, :value 4, :rank 4})) => true)
  (fact "Three of a kind"
        (has-three-of-a-kind? '( {:suit \D, :value 4, :rank 4}
                                 {:suit \C, :value \A, :rank 14}
                                 {:suit \C, :value 4, :rank 4}
                                 {:suit \C, :value 5, :rank 5}
                                 {:suit \S, :value 4, :rank 4})) => true)
  (fact "Four of a kind"
        (has-four-of-a-kind? '( {:suit \D, :value 4, :rank 4}
                                {:suit \C, :value 4, :rank 4}
                                {:suit \C, :value 4, :rank 4}
                                {:suit \C, :value 5, :rank 5}
                                {:suit \S, :value 4, :rank 4})) => true)
  (fact "Two pairs"
        (has-two-pairs? '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \S, :value 4, :rank 4})) => true)
  (fact "Full house has Two pairs"
        (has-two-pairs? '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \S, :value 4, :rank 4})) => true)
  (fact "Straight"
        (has-straight? '( {:suit \D, :value 4, :rank 4}
                          {:suit \C, :value 3, :rank 3}
                          {:suit \C, :value 6, :rank 6}
                          {:suit \C, :value 5, :rank 5}
                          {:suit \S, :value 7, :rank 7})) => true)
  (fact "Flush"
        (has-flush? '( {:suit \C, :value 4, :rank 4}
                       {:suit \C, :value \A, :rank 14}
                       {:suit \C, :value 3, :rank 3}
                       {:suit \C, :value 6, :rank 6}
                       {:suit \C, :value 7, :rank 7})) => true)
)

  
(facts "Resolve points"
  (fact "High card"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 5, :rank 5}
                           {:suit \S, :value 8, :rank 8})) => 0)
  (fact "Single pair"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 5, :rank 5}
                           {:suit \S, :value 4, :rank 4})) => 2)
  (fact "Two pairs"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \S, :value 4, :rank 4})) => 3)
  (fact "Three of a kind"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value 5, :rank 5}
                           {:suit \S, :value 4, :rank 4})) => 4)
  (fact "Straight"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value 3, :rank 3}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 5, :rank 5}
                           {:suit \S, :value 7, :rank 7})) => 5)
  (fact "Flush"
        (resolve-points '( {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 3, :rank 3}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 7, :rank 7})) => 6)
  (fact "Full house"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value \A, :rank 14}
                           {:suit \S, :value 4, :rank 4})) => 7)
  (fact "Four of a kind"
        (resolve-points '( {:suit \D, :value 4, :rank 4}
                           {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value 5, :rank 5}
                           {:suit \S, :value 4, :rank 4})) => 8)
  (fact "Straight flush"
        (resolve-points '( {:suit \C, :value 4, :rank 4}
                           {:suit \C, :value 3, :rank 3}
                           {:suit \C, :value 6, :rank 6}
                           {:suit \C, :value 5, :rank 5}
                           {:suit \C, :value 7, :rank 7})) => 9)
)

