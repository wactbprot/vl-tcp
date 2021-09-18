(ns wactbprot.vl-tcp
  (:require [clojure.string :as string])
  (:import [java.io BufferedReader OutputStreamWriter InputStreamReader PrintWriter]
           [java.net Socket]))

;; ........................................................................
;; generate socket 
;; ........................................................................
(defn out-socket-raw [s] (.getOutputStream s)) 

(defn out-socket [s]
  (PrintWriter. (OutputStreamWriter. (out-socket-raw s))))

(defn in-socket-raw [s] (.getInputStream s))

(defn in-socket [s]
  (BufferedReader. (InputStreamReader. (in-socket-raw s))))

(defn gen-socket [h p] (Socket. h p))

;; ........................................................................
;; read fns
;; ........................................................................
(defn read-line [in] (.readLine in))

(defn read-eot [in i]
  (string/join (loop [c (.read in) v []]
                 (if (not= c i)
                   (recur (.read in) (conj v (char c)))
                   v))))

(defn read-bytes [in n]
  (into [] (for [_ (range n)] (.read in))))

(defn read-lines [in n]
  (string/join (into [] (for [_ (range n)] (read-line in)))))


;; ........................................................................
;; write fns
;; ........................................................................

(defn write-bytes [out cmd]
  (.write out cmd 0 (count cmd))
  (.flush out))

(defn write-str [out cmd]
  (.print out cmd)
  (.flush out))     

