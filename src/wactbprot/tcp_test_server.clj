(ns wactbprot.tcp-test-server
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Provides a basic TCP Server for test purposes."}
  (:require [clojure.java.io :as io])
  (:import [java.net ServerSocket]))

(defn rd
  "Reads from socket `s` until a `\n` `\r` or `\r\n` occurs.

  NOTE: the `\n` `\r` or `\r\n` are eaten by `.readLine`; they are not
  returned."
  [s]
  (.readLine (io/reader s)))

(defn wrt
  "Send the given string `msg` out over the given socket `s`."
  [s msg]
  (let [writer (io/writer s)]
      (.write writer msg)
      (.flush writer)))

(defn server
  "Starts a echo server at localhost port 9999."
  []
  (let [running (atom true)]
    (future (with-open [server-sock (ServerSocket. 9999)]
              (while @running
                (with-open [sock (.accept server-sock)]
                  (let [x (rd sock)]
                    (Thread/sleep 1000)
                    (wrt sock x))))))
    running))
