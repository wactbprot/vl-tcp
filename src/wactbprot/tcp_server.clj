(ns wactbprot.tcp-server
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Provides a basic TCP Server for test purposes following
    https://github.com/clojure-cookbook/clojure-cookbook/blob/master/05_network-io/5-10_tcp-server.asciidoc"}
  (:require [clojure.java.io :as io])
  (:import [java.net ServerSocket]))

(defn r [socket]
  (.readLine (io/reader socket)))

(defn s
  "Send the given string message out over the given socket"
  [socket msg]
  (let [writer (io/writer socket)]
      (.write writer msg)
      (.flush writer)))

(defn server []
  (let [running (atom true)]
    (future (with-open [server-sock (ServerSocket. 9999)]
              (while @running
                (with-open [sock (.accept server-sock)]
                  (s sock (r sock))))))
    running))

(comment
  ;; start server by
  (def srv (server))
  ;; stop server by
  (reset! srv false)
  ;; afterwards the server responses one more time!
  )
