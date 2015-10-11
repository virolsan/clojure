(ns training._4clojure.core)

; fibonacci rekursiolla
(defn my_fibonacci [cnt] 
  ((fn foo [s pre cur i cnt]
    (if (<= i cnt)
      (foo (conj s cur) cur (+ pre cur) (inc i) cnt)
      s)) [] 0 1 1 cnt))
(my_fibonacci 8)  ; '(1 1 2 3 5 8 13 21)

(defn my_fibo_n [c]
  (apply + (take 2 (reverse c))))
(my_fibo_n [1])


; 4clojure problems

; #16
(defn hello [x] (str "Hello, " x "!"))

; #21
(defn my_nth [x n] 
  (get (vec x) n))

; #134
(defn my_value_exists [y x] 
  (and (contains? x y) (nil? (get x y))))

; #156
(defn my_map_init [defaultvalue keyseq] 
  (into {} (map #(hash-map % defaultvalue) keyseq)))
(my_map_init 1 [1 2])
; (into {} (map vector [1 2] (repeat 1)))
#(reduce (fn [m v]
            (assoc m v %1))
        {} %2)

; #38
(defn my_max_value [& x] 
  (reduce #(if (> %1 %2) 
             %1 
             %2) 
    x))
(my_max_value 1 4 3)

; #22
(defn my_count_reduce [x]
  (reduce (fn [a _] (inc a)) 0 x)) ; '_' = unused parameter
(my_count_reduce '(1 2 3 3 1))
(my_count_reduce "Hello World")
(defn my_count_rec [x] 
  ((fn c_rec [sq cnt] 
    (if (= nil (first sq))
      cnt
      (c_rec (rest sq) (inc cnt)))
    ) x 0))
(my_count_rec '(1 2 3 3 1))
(my_count_rec "Hello World")

; #23
(defn my_reverse [x]
  (into () x))
(my_reverse [1 2 3 4 5])

; #29
(#(apply str (re-seq #"[A-Z]+" %)) "HePPuLi")

; #34
(defn my_range [fro til]
  ((fn foo [s cur til]
    (if (< cur til)
      (foo (conj s cur) (inc cur) til)
      s)) [] fro til))
(my_range 1 4)
; using iterate
(#(take (- %2 %) (iterate inc %)) 1 4)

; #27
(defn my_palindrome [s] (= (seq s) (reverse s)))
(my_palindrome [1 2 3 4 5])
(my_palindrome "racecar")

; #32
(defn my_duplicate [s]
  (sort (concat s s)))
(my_duplicate [1 2 3]) ; '(1 1 2 2 3 3))
(my_duplicate [[1 2] [3 4]]) ; '([1 2] [1 2] [3 4] [3 4])
; using mapcat
(mapcat #(list % %) [[1 2] [3 4]])

; #28
(defn my_flatten [s]
  (mapcat #(if (coll? %) 
           (my_flatten %)
           (list %)) s))
(my_flatten '((1 2) 3 [4 [5 6]])) ; '(1 2 3 4 5 6)
(my_flatten [[1 2]]) ; '(1 2 3 4 5 6)

; #39
(defn my_interleave [s1 s2]
  (flatten (map (comp concat list) s1 s2)))
(my_interleave [30 20] [25 15]) ; [30 25 20 15]
; using mapcat
(mapcat list [1 2 3] [4 5 6])
(interleave [30 20] [25 15])

; #42
(defn my_factorial [n]
  (reduce * (range 1 (inc n))))
(my_factorial 8) ; 40320

; #83
(defn my_some_truth [& params] (= 2 (count (distinct params))))
(my_some_truth true true)
(my_some_truth true false)
; using not=
(not= true true false true)

; #30
(defn my_consecutive_distinct [s]
  (reduce (fn [s v] 
						(if (not= v (last s))
						  (conj s v)
					    s)) [] s))
(my_consecutive_distinct [1 1 2 3 3 2 2 3]) ; '(1 2 3 2 3)
; using partition-by
(map last (partition-by list [1 1 2 3 3 2 2 3]))

; #49
(defn my_split_at [i s]
  (#(into[] (concat (list (first (partition i %))) (list (apply concat (rest (partition-all i %)))))) s))
(my_split_at 3 [1 2 3 4 5 6]) ; [[1 2 3] [4 5 6]]
; take'n'drop approach
(#(into[] (concat (list (take %1 %2)) (list (drop %1 %2)))) 3 [1 2 3 4 5 6])
; vector approach
((fn [n s] [(take n s) (drop n s)]) 3 [1 2 3 4 5 6])
; using juxt
((juxt take drop) 3 [1 2 3 4 5 6])


; #33
(defn my_replicate [s n]
  (mapcat #((fn f [t v n] 
              (if (> n 0)
                (f (conj t %) v (dec n))
                t)) [] % n) s))
(my_replicate [1 2 3] 2) ; '(1 1 2 2 3 3)
; using repeat
(mapcat #(repeat 2 %) [1 2 3])

; #40
(defn my_interpose [a s] (rest (mapcat #(list a %) s)))
(my_interpose 0 [1 2 3]) ; [1 0 2 0 3]
; using interleave
(#(butlast (interleave %2 (repeat %1))) 0 [1 2 3])

; #41
(defn my_drop_nths [s n]
  (mapcat #(take (dec n) %) (partition-all n s)))
(my_drop_nths [1 2 3 4 5 6 7 8] 3) ; [1 2 4 5 7 8]

; #61
(defn my_zipmap [s1 s2]
  (apply merge (map hash-map s1 s2)))
(my_zipmap [:a :b :c] [1 2 3]) ; {:a 1, :b 2, :c 3}
; using interleave
(apply hash-map (interleave [:a :b :c] [1 2 3]))

; #81
(defn my_intersect [s1 s2]
  (into #{} (filter (partial contains? s2) s1)))
(my_intersect #{0 1 2 3} #{2 3 4 5}) ; #{2 3}



; KESKENERÄISET

; #66
(defn my_greatestcommondivisor [x y]
  (#(or (= (mod %1 %2) 0) (= (mod %2 %1) 0)) x y))
(my_greatestcommondivisor 2 4)

(#(first (list %1 %2)) 12 20)
(#(range 1 (inc (first (list %1 %2)))) 12 20)
(filter #(even? %) [1 2 3 4 5 6])
;((-> (partial filter #(even? %)) #(apply conj [] (range 1 (inc (first (list %1 %2)))))) 12 20)

;((partial filter (fn [a b] (or (= (mod a b) 0) (= (mod b a) 0)))) (fn [x y] (range 1 (inc (first (list x y))))) 12 20)

(defn my_common_divisor? [num div] (= (mod num div) 0))
(my_common_divisor? 4 2)

; TIPS'N'TRICKS

; with sequential operations, last value is returned
; do
(do (println "Hello.") (println "Hello again.") (+ 2 2))
; fn
((fn [] (println "Here we are") (+ 1 2)))
; when (note! there's no else)
(when true (+ 1 1) (- 1 1) (* 1 1))
; let
(let [color "Red" phrase (str "Color is: " color)] (str "Silence!") (str "Clojure says: " phrase))

; repeat
(mapcat #(repeat 2 %) [1 2 3]) ;#33

; using loops
; loop/recur
(loop [i 0]
  (when (< i 5)
    (print i)
    (recur (inc i))))
; doseq
(doseq [i (range 0 5)] (print i))

; function may have multiple signatures
(defn factorial 
    ([n] (factorial n 1)) 
    ([n acc] (if (= n 0) 
               acc 
               (recur (dec n) (* acc n)))))

; juxt
((juxt take drop) 3 [1 2 3 4 5 6]) ; [[1 2 3] [4 5 6]]

; partition-by, ...
(partition-by list [1 1 2 3 3 2 2 3])
(#(into[] (concat (partition 4 %) (list (apply concat (rest (partition-all 4 %)))))) [1 2 3 4 5 6])

(partition 3 [1 2 3 4 5 6])
(rest (partition-all 3 [1 2 3 4 5 6]))
(apply concat (rest (partition-all 1 [1 2 3 4 5 6])))
(rest (partition-all 2 [[1 2] [3 4] [5 6]]))

; map
(map println [1 2 3] [4 5 6])
(flatten (map (comp concat list) [1 2 3] [4 5 6]))

; mapcat
(mapcat #(list % %) [[1 2] [3 4]]) ;#32
(mapcat list [1 2 3] [4 5 6]) ;#39

; iterate
(#(take (- %2 %) (iterate inc %)) 1 4)

; dotimes
(defn my_range_print [begin end]
  (dotimes [n (- end begin)]
    (print (str (+ begin n) " "))))
;(my_range_print -1 6)

; SPECIAL STUFF (http://en.wikibooks.org/wiki/Clojure_Programming/By_Example)

; mutating permanent state variables
(def r (ref 0))
(dosync (ref-set r 5))
@r

; asynchronous calls (agents)
(def a (agent 1))
(send a inc)
(await a)
@a

; using mutable state - local thread-safe variable
(let [secret (ref "nothing")] 
  (defn read-secret [] @secret) 
  (defn write-secret [s] (dosync (ref-set secret s))))
(read-secret)
(write-secret "Hi");
(read-secret)