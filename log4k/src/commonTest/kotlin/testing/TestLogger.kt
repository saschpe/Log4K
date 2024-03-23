package testing

import saschpe.log4k.Log
import saschpe.log4k.Logger

class TestLogger : Logger() {
    var level: Log.Level? = null
    var message: String? = null
    var tag: String? = null
    var throwable: Throwable? = null

    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        this.level = level
        this.tag = tag
        this.message = message
        this.throwable = throwable
    }
}
