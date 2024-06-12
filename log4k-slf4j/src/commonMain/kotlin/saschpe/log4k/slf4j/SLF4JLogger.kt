package saschpe.log4k.slf4j

import saschpe.log4k.Log
import saschpe.log4k.Logger

/**
 * Logger that forwards all log statements to SLF4J API on JVM and Android platforms.
 *
 * Suitable to replace [saschpe.log4k.ConsoleLogger]:
 *
 * ```kotlin
 * Log.loggers.clear()
 * Log.loggers += SLF4JLogger()
 * ```
 *
 * On JVM or Android, SLF4J is used for logging. On JS or Apple platforms, console logging is used internally.
 */
expect class SLF4JLogger : Logger {
    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?)
}
