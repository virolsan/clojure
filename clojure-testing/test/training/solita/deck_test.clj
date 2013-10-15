(ns training.solita.deck-test
  (:use [training.solita.deck]
        [midje.sweet]))

(facts
  (fact (count (make-deck)) => 52)
  (fact (has-pair (take 5 (make-deck))) => false)
  (fact (has-pair (make-deck)) => true))

