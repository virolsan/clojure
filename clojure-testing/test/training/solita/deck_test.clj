(ns training.solita.deck-test
  (:use [training.solita.deck]
        [midje.sweet]))

(let [low-card '( {:suit \D, :value 4, :rank 4}
                  {:suit \C, :value 2, :rank 2}
                  {:suit \C, :value 6, :rank 6}
                  {:suit \C, :value 5, :rank 5}
                  {:suit \S, :value 8, :rank 8})
      high-card '( {:suit \D, :value 4, :rank 4}
                   {:suit \C, :value \A, :rank 14}
                   {:suit \C, :value 6, :rank 6}
                   {:suit \C, :value 5, :rank 5}
                   {:suit \S, :value 8, :rank 8})
      one-pair '( {:suit \D, :value 4, :rank 4}
                  {:suit \C, :value 4, :rank 4}
                  {:suit \C, :value 6, :rank 6}
                  {:suit \C, :value 5, :rank 5}
                  {:suit \S, :value 8, :rank 8})
      one-pair-high-card '( {:suit \D, :value 4, :rank 4}
                            {:suit \C, :value 4, :rank 4}
                            {:suit \C, :value \A, :rank 14}
                            {:suit \C, :value 5, :rank 5}
                            {:suit \S, :value 8, :rank 8})
      three-of-a-kind '( {:suit \D, :value 4, :rank 4}
                         {:suit \C, :value \A, :rank 14}
                         {:suit \C, :value 4, :rank 4}
                         {:suit \C, :value 5, :rank 5}
                         {:suit \S, :value 4, :rank 4})
      four-of-a-kind '( {:suit \D, :value 4, :rank 4}
                        {:suit \C, :value 4, :rank 4}
                        {:suit \C, :value 4, :rank 4}
                        {:suit \C, :value 5, :rank 5}
                        {:suit \S, :value 4, :rank 4})
      two-pairs '( {:suit \D, :value 4, :rank 4}
                   {:suit \C, :value \A, :rank 14}
                   {:suit \C, :value 6, :rank 6}
                   {:suit \C, :value 6, :rank 6}
                   {:suit \S, :value 4, :rank 4})
      full-house '( {:suit \D, :value 4, :rank 4}
                    {:suit \C, :value 4, :rank 4}
                    {:suit \C, :value 6, :rank 6}
                    {:suit \C, :value 6, :rank 6}
                    {:suit \S, :value 4, :rank 4})
      straight '( {:suit \D, :value 4, :rank 4}
                  {:suit \C, :value 3, :rank 3}
                  {:suit \C, :value 6, :rank 6}
                  {:suit \C, :value 5, :rank 5}
                  {:suit \S, :value 7, :rank 7})
      flush '( {:suit \C, :value 4, :rank 4}
               {:suit \C, :value \A, :rank 14}
               {:suit \C, :value 3, :rank 3}
               {:suit \C, :value 6, :rank 6}
               {:suit \C, :value 7, :rank 7})
      straight-flush '( {:suit \C, :value 4, :rank 4}
                        {:suit \C, :value 3, :rank 3}
                        {:suit \C, :value 6, :rank 6}
                        {:suit \C, :value 5, :rank 5}
                        {:suit \C, :value 7, :rank 7})
      royal-flush '( {:suit \C, :value 10, :rank 10}
                     {:suit \C, :value \J, :rank 11}
                     {:suit \C, :value \Q, :rank 12}
                     {:suit \C, :value \K, :rank 13}
                     {:suit \C, :value \A, :rank 14})]

  (facts "General"
         (fact "Size of deck" 
               (count (make-deck)) => 52))

  (facts "Positive match"
         (fact "Single pair"
               (has-pair? one-pair) => true)
         (fact "Three of a kind"
               (has-three-of-a-kind? three-of-a-kind) => true)
         (fact "Four of a kind"
               (has-four-of-a-kind? four-of-a-kind) => true)
         (fact "Two pairs"
               (has-two-pairs? two-pairs) => true)
         (fact "Full house has Two pairs"
               (has-two-pairs? full-house) => true)
         (fact "Straight"
               (has-straight? straight) => true)
         (fact "Flush"
               (has-flush? flush) => true))
  
  (facts "Negative match"
         (fact "Single pair"
               (has-pair? high-card) => false)
         (fact "Three of a kind"
               (has-three-of-a-kind? one-pair) => false)
         (fact "Four of a kind"
               (has-four-of-a-kind? three-of-a-kind) => false)
         (fact "Two pairs"
               (has-two-pairs? one-pair) => false)
         (fact "Straight"
               (has-straight? high-card) => false)
         (fact "Flush"
               (has-flush? straight) => false))
  
  (facts "Resolve points"
         (fact "High card"
               (resolve-points high-card) => 0)
         (fact "Single pair"
               (resolve-points one-pair) => 2)
         (fact "Two pairs"
               (resolve-points two-pairs) => 3)
         (fact "Three of a kind"
               (resolve-points three-of-a-kind) => 4)
         (fact "Straight"
               (resolve-points straight) => 5)
         (fact "Flush"
               (resolve-points flush) => 6)
         (fact "Full house"
               (resolve-points full-house) => 7)
         (fact "Four of a kind"
               (resolve-points four-of-a-kind) => 8)
         (fact "Straight flush"
               (resolve-points straight-flush) => 9)
         (fact "Royal flush"
               (resolve-points royal-flush) => 10))

  (facts "Resolve better hand"
         (fact "High card vs. One pair"
               (resolve-better-hand high-card one-pair) => one-pair)
         (fact "Low card vs. High card"
               (resolve-better-hand low-card high-card) => high-card)
         (fact "High card vs. Low card"
               (resolve-better-hand high-card low-card) => high-card)
         (fact "One pair with High card vs. One pair"
               (resolve-better-hand one-pair-high-card one-pair) => one-pair-high-card)
         (fact "One pair vs. One pair with High card"
               (resolve-better-hand one-pair one-pair-high-card) => one-pair-high-card))
         (fact "Draw"
               (resolve-better-hand one-pair one-pair) => nil)
)

