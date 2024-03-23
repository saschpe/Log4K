package saschpe.log4k

import android.util.Log as AndroidLog

actual class ConsoleLogger : Logger() {
    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        val logTag = tag.ifEmpty { getTraceTag() }
        when (level) {
            Log.Level.Verbose -> AndroidLog.v(logTag, message, throwable)
            Log.Level.Debug -> AndroidLog.d(logTag, message, throwable)
            Log.Level.Info -> AndroidLog.i(logTag, message, throwable)
            Log.Level.Warning -> AndroidLog.w(logTag, message, throwable)
            Log.Level.Error -> AndroidLog.e(logTag, message, throwable)
            Log.Level.Assert -> AndroidLog.wtf(logTag, message, throwable)
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
}
