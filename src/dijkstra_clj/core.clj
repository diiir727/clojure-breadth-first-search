(ns dijkstra-clj.core)

(def matrix-test
  {
   0 {0 0  1 1  2 5  3 9}
   1 {0 0  1 0  2 1  3 0}
   2 {0 0  1 0  2 0  3 1}
   3 {0 0  1 0  2 0  3 0}
   })


(defn get-best-weight [node best-paths] (get (get best-paths node) :weight 0))

(defn isNeedRelax [currWeight weight bestPath]
  ;weight != 0 && (bestPath == 0 || currWeight + weight < bestPath)
  (and
    (not= weight 0)
    (or
      (zero? bestPath)
      (> bestPath (+ currWeight weight)))))

(defn relax [bestPaths queue node to-node weight]
  {
   :best-paths (assoc bestPaths to-node {:node node :weight weight})
   :queue [{:node to-node :weight weight}] })

(defn relax-edges [curr-node curr-weight best-paths graph queue]
  (map
    (fn [[node weight]]
      (when
        (isNeedRelax curr-weight weight (get-best-weight node best-paths))
        (relax best-paths queue curr-node node (+ curr-weight weight))))
    (get graph curr-node)))

(defn relax-loop [curr-node curr-weight best-paths graph queue]
  (let [data-struct (relax-edges curr-node curr-weight best-paths graph queue)]
    {
     :best-paths (into {} (concat best-paths (filter (fn [x] (not (nil? x))) (map :best-paths data-struct))))
     :queue (into #{} (concat queue (flatten (filter (fn [x] (not (nil? x))) (map :queue data-struct)))))
     }))

(defn print-best-path [paths from to]
  (loop [curr to res [to]]
    (if (not (= from curr))
      (recur (:node (get paths curr)) (conj res (:node (get paths curr))))
      {:nodes (reverse res) :weight (:weight (get paths to))})))

(defn find-path [from to graph]
  (loop [my-struct {:queue #{{:node from :weight 0}} :best-paths {}} ]
    (if (not (empty? (:queue my-struct)))
      (let [
            current (first (:queue my-struct))
            curr-node (:node current)
            curr-weight (:weight current)
            best-paths (:best-paths my-struct)
            queue (:queue my-struct)
            ]

        (recur (relax-loop curr-node curr-weight best-paths graph (next queue))))
      (print-best-path (:best-paths my-struct) from to))))

