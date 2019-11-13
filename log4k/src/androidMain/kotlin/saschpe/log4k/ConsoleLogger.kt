package saschpe.log4k

import android.util.Log as AndroidLog

actual class ConsoleLogger : Logger() {
    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        val trace = Exception().stackTrace[5]
        var logTag = tag
        if (tag.isEmpty()) {
            logTag = getTraceTag(trace)
        }

        when (level) {
            Log.Level.Verbose -> AndroidLog.v(logTag, message, throwable)
            Log.Level.Debug -> AndroidLog.d(logTag, message, throwable)
            Log.Level.Info -> AndroidLog.i(logTag, message, throwable)
            Log.Level.Warning -> AndroidLog.w(logTag, message, throwable)
            Log.Level.Error -> AndroidLog.e(logTag, message, throwable)
            Log.Level.Assert -> AndroidLog.wtf(logTag, message, throwable)
        }
    }

    private fun getTraceTag(trace: StackTraceElement): String {
        val className = trace.className.split(".").last()
        return "$className.${trace.methodName}"
    }
}
