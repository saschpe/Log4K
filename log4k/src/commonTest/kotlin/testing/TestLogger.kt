package testing

import saschpe.log4k.Log
import saschpe.log4k.Logger
import kotlin.test.assertEquals

const val TEST_MESSAGE = "Test message"
const val TEST_TAG = "TAG"
val TEST_THROWABLE = Exception("Test")

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

val testLogger: TestLogger
    get() = Log.loggers.first() as TestLogger

fun assertTestLogger(
    expectedLogLevel: Log.Level,
    expectedMessage: String? = TEST_MESSAGE,
    expectedTag: String = TEST_TAG,
    expectedThrowable: Throwable? = TEST_THROWABLE,
) {
    assertEquals(expectedLogLevel, testLogger.level)
    assertEquals(expectedMessage, testLogger.message)
    assertEquals(expectedTag, testLogger.tag)
    assertEquals(expectedThrowable, testLogger.throwable)
}
