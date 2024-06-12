package saschpe.log4k.slf4j

import saschpe.log4k.ConsoleLogger
import saschpe.log4k.Log
import saschpe.log4k.Logger

actual class SLF4JLogger : Logger() {
    private val logger = ConsoleLogger()

    actual override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) =
        logger.log(level, tag, message, throwable)
}
