package saschpe.log4k

import java.io.PrintWriter
import java.io.StringWriter
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.SimpleFormatter
import java.util.logging.Logger as JavaLogger

actual class ConsoleLogger : Logger() {
    actual override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        val logTag = tag.ifEmpty { getTraceTag() }

        var fullMessage = "$message"
        throwable?.let { fullMessage = "$fullMessage\n${it.stackTraceString}" }
        val msg = "${levelMap[level]} $logTag: $fullMessage"

        when (level) {
            Log.Level.Verbose -> javaLogger.finest(msg)
            Log.Level.Debug -> javaLogger.fine(msg)
            Log.Level.Info -> javaLogger.info(msg)
            Log.Level.Warning -> javaLogger.warning(msg)
            Log.Level.Error -> javaLogger.severe(msg)
            Log.Level.Assert -> javaLogger.severe(msg)
        }
    }

    private fun getTraceTag(): String {
        var trace = Exception().stackTrace[6]
        var className = trace.className.split(".").last()

        // Corner-case when the Log.log function is used instead of Log.debug, Log.ve
        if (className == "NativeMethodAccessorImpl") {
            trace = Exception().stackTrace[5]
            className = trace.className.split(".").last()
        }
        return "$className.${trace.methodName}"
    }

    private val Throwable.stackTraceString
        get(): String {
            val sw = StringWriter(256)
            val pw = PrintWriter(sw, false)
            printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }

    private val levelMap: HashMap<Log.Level, String> = hashMapOf(
        Log.Level.Verbose to "${AnsiColor.White.value}Verbose${AnsiColor.Reset.value}",
        Log.Level.Debug to "${AnsiColor.Blue.value}Debug${AnsiColor.Reset.value}",
        Log.Level.Info to "${AnsiColor.Green.value}Info${AnsiColor.Reset.value}",
        Log.Level.Warning to "${AnsiColor.Yellow.value}Warn${AnsiColor.Reset.value}",
        Log.Level.Error to "${AnsiColor.Red.value}Error${AnsiColor.Reset.value}",
        Log.Level.Assert to "${AnsiColor.Magenta.value}Assert${AnsiColor.Reset.value}",
    )

    companion object {
        internal val javaLogger = JavaLogger.getLogger(ConsoleLogger::class.java.name).apply {
            level = Level.ALL
            System.setProperty("java.util.logging.SimpleFormatter.format", "%1\$tF %1\$tT %5\$s %n")
            addHandler(
                ConsoleHandler().apply {
                    level = Level.ALL
                    formatter = SimpleFormatter()
                },
            )
            useParentHandlers = false
        }
    }
}
