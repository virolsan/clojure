(ns training.solita.deck-test
  (:use [training.solita.deck]
        [midje.sweet]))

(facts
  (fact (count (make-deck)) => 52))

