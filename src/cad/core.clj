(ns cad.core
  (:require [clojure.java.io :refer [make-parents]]
            [scad-clj.scad :refer [write-scad]]
            [scad-clj.model :refer [cube difference union translate cylinder
                                    with-fn]])
  (:gen-class))

(defn degrees [n] (* n (/ Math/PI 180)))

(defn render!
  [file-name part]
  (let [file-path (str "out/" file-name ".scad")]
    (make-parents file-path)
    (spit file-path
          (write-scad part))))

(defn config
  ([] (config {}))
  ([opts]
  (let [opts (merge {:pretty? false
                     :battery-x 11.5
                     :battery-z 6.5
                     :sleave-y 20
                     :sleave-thickness 1.5
                     :tab-x 5
                     :tab-y 20
                     :tab-z 1.5
                     :tab-gap-z 2
                     :tab-post-radius 4
                     }
                    opts)
        battery-x (:battery-x opts)
        battery-z (:battery-z opts)
        sleave-thickness (:sleave-thickness opts)
        opts (assoc opts
                    :sleave-x (+ battery-x sleave-thickness sleave-thickness)
                    :sleave-z (+ battery-z sleave-thickness sleave-thickness)
                    )
      ]
    opts)))

(defn sleave
  [{:keys [sleave-x sleave-z sleave-y battery-x battery-z sleave-thickness]}]
  (difference
  (cube sleave-x sleave-y sleave-z)
  (cube battery-x 1000 battery-z)
  ))

(defn tab
  [{:keys [tab-x tab-y tab-z tab-gap-z sleave-z tab-post-radius]}]

  (union
    (translate [0 0 (+ (/ (+ sleave-z tab-z) 2) tab-gap-z )]
               (cube tab-x tab-y tab-z))
    (translate [0 0 (+ (/ (+ sleave-z tab-gap-z) 2))]
               (with-fn 50
                        (cylinder tab-post-radius tab-gap-z)))))


(defn thing
  [config]
  (union
  (sleave config)
  (tab config)
  ))

(render! "thing" (thing (config)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
