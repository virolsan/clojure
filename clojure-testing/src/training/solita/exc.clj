(ns training.solita.exc)

(defn hello-world
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn luku-on-yli-kymmenen [luku]
  (if (> luku 10) 
    (println "Kyllä")
    (println "Ei")
  ))

(defn make-hashes [n]
  (if (= n 0)
    ""
    (str "#" (make-hashes (dec n)))))

(defn draw-hashes [n] 
  (println (make-hashes n)))

(defn draw-triangle [height]
  (if (> height 1)
    (draw-triangle (dec height)))
  (draw-hashes height))

(defn to-exponentiation [x y] 
  (if (= 0 y)
    1
    (* x (to-exponentiation x (dec y)))))

(to-exponentiation 2 3) ; cmd + enter -> evaluoi lausekkeen