package saschpe.log4k

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSThread

actual class ConsoleLogger : Logger() {
    private val dateFormatter = NSDateFormatter().apply { dateFormat = "MM-dd HH:mm:ss.SSS" }

    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        val trace = NSThread.callStackSymbols()[5] as String
        var logTag = tag
        if (tag.isEmpty()) {
            logTag = getTraceTag(trace)
        }
        println("${getCurrentTime()} ${levelMap[level]} $logTag: $message")
    }

    private fun getCurrentTime() = dateFormatter.stringFromDate(NSDate())

    private fun getTraceTag(trace: String) = trace.split("\$").last().split(" ").first()

    private val levelMap: HashMap<Log.Level, String> = hashMapOf(
        Log.Level.Verbose to "Verbose",
        Log.Level.Debug to "💙 Debug",
        Log.Level.Info to "💚 Info",
        Log.Level.Warning to "💛 Warn",
        Log.Level.Error to "❤️ Error",
        Log.Level.Assert to "💜 Assert",
    )
}
