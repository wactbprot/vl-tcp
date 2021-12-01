(ns wactbprot.vl-tcp
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Provides basic socket functions such as open, close, read write."}
  (:require [clojure.string :as string])
  (:import [java.io BufferedReader OutputStreamWriter InputStreamReader PrintWriter]
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
(defn gen-socket [h p] (Socket. h p))

(defn close-socket [s] (.close s))

;; ........................................................................
;; read fns
;; ........................................................................
(defn rd-eot [in i]
  (string/join (loop [c (.read in) v []]
                 (if (not= c i)
                   (recur (.read in) (conj v (char c)))
                   v))))

(defn rd-bytes [in n]
  (into [] (for [_ (range n)] (.read in))))

(defn rd-line [in] (.readLine in))

(defn rd-lines [in n]
  (string/join (into [] (for [_ (range n)] (rd-line in)))))

;; ........................................................................
;; write fns
;; ........................................................................
(defn write-bytes [out cmd]
  (.write out cmd 0 (count cmd))
  (.flush out))

(defn write-str [out cmd]
  (.print out cmd)
  (.flush out))     

;; ........................................................................
;; playground
;; ........................................................................
(comment
  ;; run server in wactbprot.tcp-server ns
  (def srv (server))
    
  (let [sock (gen-socket "localhost" 9999)
        in-sock (in-socket sock)
        out-sock (out-socket sock)]
    (write-str out-sock "foo\n")
    (prn  (rd-line in-sock))
    (close-socket sock)
    (close-socket in-sock)
    (close-socket out-sock)))
