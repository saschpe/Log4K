package saschpe.log4k

/**
 * Log to the platform-specific console.
 */
expect class ConsoleLogger() : Logger {
    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?)
}
