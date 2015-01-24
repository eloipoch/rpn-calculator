(ns rpn-calculator.core
  [:use [clojure [string :only [split]]]])


(def ^:private allowed-opeators {"+" + "-" - "*" * "/" quot})


(defn- parse-integer [s] (Integer. (re-find #"\d+" s)))

(defn- parse-token [token]
  (if-let [operator (get allowed-opeators token)]
    operator
    (parse-integer token)))

(defn- stack-integer [stack integer] (conj stack integer))

(defn- calc [operand stack]
  (conj (vec (drop-last 2 stack))
        (apply operand (take-last 2 stack))))

(defn- process-token [stack value]
  (if (integer? value)
    (stack-integer stack value)
    (calc value stack)))

(defn- parse-input [input]
  (map parse-token
       (split input #"\s")))


(defn evaluate [input]
  (peek
    (reduce
      process-token
      []
      (parse-input input))))
