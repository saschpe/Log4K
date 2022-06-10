package saschpe.log4k

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Log {
    enum class Level { Verbose, Debug, Info, Warning, Error, Assert }

    val loggers = mutableListOf<Logger>()

    fun verbose(message: String, throwable: Throwable? = null, tag: String = "") =
        log(Level.Verbose, tag, throwable, message)

    fun verbose(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Verbose, tag, throwable, message())

    fun info(message: String, throwable: Throwable? = null, tag: String = "") =
        log(Level.Info, tag, throwable, message)

    fun info(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Info, tag, throwable, message())

    fun debug(message: String, throwable: Throwable? = null, tag: String = "") =
        log(Level.Debug, tag, throwable, message)

    fun debug(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Debug, tag, throwable, message())

    fun warn(message: String, throwable: Throwable? = null, tag: String = "") =
        log(Level.Warning, tag, throwable, message)

    fun warn(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Warning, tag, throwable, message())

    fun error(message: String, throwable: Throwable? = null, tag: String = "") =
        log(Level.Error, tag, throwable, message)

    fun error(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Error, tag, throwable, message())

    fun assert(message: String, throwable: Throwable? = null, tag: String = "") =
        log(Level.Assert, tag, throwable, message)

    fun assert(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Assert, tag, throwable, message())

    fun log(priority: Level, tag: String = "", throwable: Throwable? = null, message: String? = null) =
        loggers.forEach { it.log(priority, tag, message, throwable) }
}

fun Any.logged(level: Log.Level = Log.Level.Debug) =
    apply { Log.log(level, message = toString(), tag = this::class.simpleName ?: "") }
