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
