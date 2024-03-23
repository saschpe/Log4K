package saschpe.log4k

import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Log {
    enum class Level { Verbose, Debug, Info, Warning, Error, Assert }

    @JvmField
    val loggers = mutableListOf<Logger>(ConsoleLogger())

    /**
     * Returns true if any [Logger.minimumLogLevel] is [Log.Level.Verbose] or [Log.Level.Debug]
     *
     * Similar to SLF4Js `LOG.isDebugEnabled`
     */
    val isDebugEnabled
        get() = loggers.any { it.minimumLogLevel <= Level.Debug }

    @JvmOverloads
    @JvmStatic
    fun verbose(message: String = "", throwable: Throwable? = null, tag: String = "") =
        log(Level.Verbose, tag, throwable, message)

    @JvmOverloads
    @JvmStatic
    fun verbose(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Verbose, tag, throwable, message())

    @JvmOverloads
    @JvmStatic
    fun info(message: String = "", throwable: Throwable? = null, tag: String = "") =
        log(Level.Info, tag, throwable, message)

    @JvmOverloads
    @JvmStatic
    fun info(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Info, tag, throwable, message())

    @JvmOverloads
    @JvmStatic
    fun debug(message: String = "", throwable: Throwable? = null, tag: String = "") =
        log(Level.Debug, tag, throwable, message)

    @JvmOverloads
    @JvmStatic
    fun debug(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Debug, tag, throwable, message())

    @JvmOverloads
    @JvmStatic
    fun warn(message: String = "", throwable: Throwable? = null, tag: String = "") =
        log(Level.Warning, tag, throwable, message)

    @JvmOverloads
    @JvmStatic
    fun warn(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Warning, tag, throwable, message())

    @JvmOverloads
    @JvmStatic
    fun error(message: String = "", throwable: Throwable? = null, tag: String = "") =
        log(Level.Error, tag, throwable, message)

    @JvmOverloads
    @JvmStatic
    fun error(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Error, tag, throwable, message())

    @JvmOverloads
    @JvmStatic
    fun assert(message: String = "", throwable: Throwable? = null, tag: String = "") =
        log(Level.Assert, tag, throwable, message)

    @JvmOverloads
    @JvmStatic
    fun assert(throwable: Throwable? = null, tag: String = "", message: () -> String) =
        log(Level.Assert, tag, throwable, message())

    @JvmOverloads
    @JvmStatic
    fun log(priority: Level, tag: String = "", throwable: Throwable? = null, message: String? = null) =
        loggers.forEach { it.log(priority, tag, message, throwable) }
}

fun Any.logged(level: Log.Level = Log.Level.Debug) =
    apply { Log.log(level, message = toString(), tag = this::class.simpleName ?: "") }
