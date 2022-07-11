package saschpe.log4k.slf4j

import org.slf4j.LoggerFactory
import saschpe.log4k.Log
import saschpe.log4k.Logger

actual class SLF4JLogger : Logger() {
    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) =
        LoggerFactory.getLogger(tag).run {
            when (level) {
                Log.Level.Verbose -> trace(message, throwable)
                Log.Level.Debug -> debug(message, throwable)
                Log.Level.Info -> info(message, throwable)
                Log.Level.Warning -> warn(message, throwable)
                Log.Level.Error -> error(message, throwable)
                Log.Level.Assert -> error(message, throwable)
            }
        }
}