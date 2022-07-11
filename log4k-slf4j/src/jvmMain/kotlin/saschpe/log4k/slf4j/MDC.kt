package saschpe.log4k.slf4j

actual object MDC {
    actual fun put(key: String, value: String) = org.slf4j.MDC.put(key, value)

    actual fun get(key: String): String? = org.slf4j.MDC.get(key)

    actual fun remove(key: String) = org.slf4j.MDC.remove(key)

    actual fun clear() = org.slf4j.MDC.clear()
}