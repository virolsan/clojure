(ns training._4clojure.core)

; fibonacci rekursiolla
(defn my_fibonacci [cnt] 
  ((fn foo [s pre cur i cnt]
    (if (<= i cnt)
      (foo (conj s cur) cur (+ pre cur) (inc i) cnt)
      s)) [] 0 1 1 cnt))
(my_fibonacci 8)  ; '(1 1 2 3 5 8 13 21)


; 4clojure problems

; #16
(defn hello [x] (str "Hello, " x "!"))

; #21
(defn my_nth [x n] 
  (get (vec x) n))

; #134
(defn my_value_exists [y x] 
  (and (contains? x y) (= nil (get x y))))

; #156
(defn my_map_init [defaultvalue keyseq] 
  (into {} (map #(hash-map % defaultvalue) keyseq)))
(my_map_init 1 [1 2])
; (into {} (map vector [1 2] (repeat 1)))

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



; KESKENERÄISET


; KOKEILUJA

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
(my_range_print -1 6)
