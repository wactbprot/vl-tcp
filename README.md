# vl-tcp

Collection of functions for TCP interaction.

## changes

* 1.0.x
** renamed `read-*` functions to `rd-*` in order to avoid `clojure.core/read-line` collision

* 2.0.x
** renamed `write-*` functions to `wrt-*`
** gen socket with timeout option (defaults to 60s)
** add logo to repo
* 2.0.8
** default socket timeout set to 10min

## Examples/ Tests

### Server
Start a echo server in `tcp-test-server`.

```clojure
(in-ns 'wactbprot.tcp-test-server)

(def srv (server))
```

Shut down the server by:

```clojure
(reset! srv false)
```

Afterwards the server responses one more time.

NOTE: The echo server is not exactly an echo server. The read and
write functions manipulate the message sent (see e.g. `rd` function
description).

### Client

After the server is started one can play around with different `wrt`
and `rd` functions. Run the client functions in the `wactbprot.vl-tcp`
namespace. Best: run a second REPL:
  
```clojure
(in-ns 'wactbprot.vl-tcp)
	
(with-open [sock (gen-socket "localhost" 9999)
             in-sock (in-socket sock) 
			 out-sock (out-socket sock)] 
	(wrt-bytes out-sock "foo\r") 
	(Thread/sleep 20) 
	(prn (rd-eot in-sock -1)))
```

```clojure
(with-open [sock     (gen-socket "localhost" 9999)
            in-sock  (in-socket sock)
            out-sock (out-socket sock)]
    (wrt-bytes out-sock "foo\r")
    (Thread/sleep 20)
    (prn (rd-line in-sock)))
```
