package saschpe.log4k

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLog
import platform.Foundation.NSThread

actual class ConsoleLogger : Logger() {
    private val dateFormatter = NSDateFormatter().apply { dateFormat = "MM-dd HH:mm:ss.SSS" }

    actual override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) =
        NSLog("${getCurrentTime()} ${levelMap[level]} ${tag.ifEmpty { getTraceTag() }}: %@",
            message ?: ""
        )

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())

    private fun getTraceTag(): String {
        val trace = NSThread.callStackSymbols()[7] as String
        return trace.split(":", limit = 2).last().split(" ", limit = 2).first() // .split("\$").last().split(" ").first()
    }

    private val levelMap: HashMap<Log.Level, String> = hashMapOf(
        Log.Level.Verbose to "Verbose",
        Log.Level.Debug to "Debug",
        Log.Level.Info to "Info",
        Log.Level.Warning to "Warn",
        Log.Level.Error to "Error",
        Log.Level.Assert to "Assert",
    )
}
