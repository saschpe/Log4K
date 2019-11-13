package saschpe.log4k

abstract class Logger {
    enum class AnsiColor(val value: String) {
        Blue("\u001B[34m"),
        Reset("\u001B[0m"),
        Green("\u001B[32m"),
        Magenta("\u001B[35m"),
        Red("\u001B[31m"),
        White("\u001B[37m"),
        Yellow("\u001B[33m")
    }

    var minimumLogLevel = Log.Level.Verbose

    fun log(level: Log.Level, tag: String = "", message: String?, throwable: Throwable?) {
        if (level.ordinal >= minimumLogLevel.ordinal) {
            print(level, tag, message, throwable)
        }
    }

    protected abstract fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?)
}
