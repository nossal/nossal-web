(ns nossal.util.base62)

; https://gist.github.com/mikelikesbikes/2297685

(def alphabet "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

(defn encode [i, alphabet]
  (reduce #(str (nth alphabet (last %2)) %) ""
    (take-while
      #(not= [0 0] %)
      (rest
        ; this is the magic (creates a lazy sequence of tuples, consisting of quot and mod)
        (iterate
          (fn [[i _]]
            (let [x (count alphabet)]
              [(quot i x) (mod i x)]))
          [i 0])))))

(defn decode [s, alphabet]
  (reduce
    (fn [val c]
      (+ (* (count alphabet) val) (.indexOf alphabet (str c))))
    0
    s))
