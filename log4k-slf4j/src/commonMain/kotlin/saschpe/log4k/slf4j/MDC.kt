package saschpe.log4k.slf4j

/**
 * Provides SLF4J's [MAppend Diagnostic Context].
 */
expect object MDC {
    fun put(key: String, value: String)
    fun get(key: String): String?
    fun remove(key: String)
    fun clear()
}
