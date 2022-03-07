(ns wactbprot.vl-tcp
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Provides basic socket functions such as open, close, read and write."}
  (:require [clojure.string :as string])
  (:import [java.io
            BufferedReader
            OutputStreamWriter
            InputStreamReader
            PrintWriter]
           [java.net Socket]))

;; ........................................................................
;; out sockets
;; ........................................................................
(defn out-socket-raw [s] (.getOutputStream s))

(defn out-socket [s]
  (PrintWriter. (OutputStreamWriter. (out-socket-raw s))))

;; ........................................................................
;; in sockets
;; ........................................................................
(defn in-socket-raw [s] (.getInputStream s))

(defn in-socket [s]
  (BufferedReader. (InputStreamReader. (in-socket-raw s))))

;; ........................................................................
;; gen, close sockets
;; ........................................................................
(defn gen-socket
  "Generates a socket at host `h` and port `p`. If no timeout `t` is
  given it defaults to 10min."
  ([h p] (gen-socket h p 600000))
  ([h p t]
   (let [s (Socket. h p)]
     (.setSoTimeout s t)
     s)))

(defn close-socket [s] (.close s))

;; ........................................................................
;; read (rd) functions
;; ........................................................................
(defn rd
  "Tries to `.read` from given socket `s`.
  Returns integers (like 97) that may be converted to chars.

  Example:
  ```clojure
  (= \a (char 97))
  ;; => true
  ```  "
  [s]
  (try (.read s)
       (catch Exception ex (.getMessage ex))))

(defn rd-eot
  "Reads from socket `in` until the integer `i` occurs.

  Example:
  ```clojure
  (rd-eot in -1)
  
  ;; (int \a)
  ;; =>
  ;; 97
  
  (rd-eot in (int \n))
  ``` "
  [in i]
  (string/join (loop [c (rd in) v []]
                 (if (not= c i)
                   (recur (rd in) (conj v (char c)))
                   v))))

(defn rd-bytes [in n]
  (into [] (for [_ (range n)] (rd in))))

(defn rd-line [s]
  (try (.readLine s)
       (catch Exception ex (.getMessage ex))))

(defn rd-lines [in n]
  (string/join (into [] (for [_ (range n)] (rd-line in)))))

;; ........................................................................
;; write fns
;; ........................................................................
(defn wrt-bytes [out cmd]
  (.write out cmd 0 (count cmd))
  (.flush out))

(defn wrt-str [out cmd]
  (.print out cmd)
  (.flush out))
