package saschpe.log4k.slf4j

actual object MDC {
    private val context = mutableMapOf<String, String>()

    actual fun put(key: String, value: String) {
        context[key] = value
    }

    actual fun get(key: String): String? = context[key]

    actual fun remove(key: String) {
        context.remove(key)
    }

    actual fun clear() = context.clear()
}