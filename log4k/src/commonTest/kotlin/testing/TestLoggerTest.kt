package testing

import saschpe.log4k.Log
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

abstract class TestLoggerTest {
    private val testLogger = TestLogger()

    @BeforeTest // Arrange
    fun before() {
        Log.loggers.clear()
        assertEquals(0, Log.loggers.size)
        Log.loggers += testLogger
        assertEquals(1, Log.loggers.size)
    }

    @AfterTest
    fun after() {
        Log.loggers.clear()
        assertEquals(0, Log.loggers.size)
    }

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

    companion object {
        const val TEST_MESSAGE = "Test message"
        const val TEST_TAG = "TAG"
        val TEST_THROWABLE = Exception("Test")
    }
}
