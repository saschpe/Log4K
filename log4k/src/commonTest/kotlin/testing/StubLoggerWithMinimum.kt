package testing

import saschpe.log4k.Log
import saschpe.log4k.Logger

class StubLoggerWithMinimum(
    minimumLogLevel: Log.Level,
) : Logger() {
    init {
        this.minimumLogLevel = minimumLogLevel
    }

    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) = Unit
}
