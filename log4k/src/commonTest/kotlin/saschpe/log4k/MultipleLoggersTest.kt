package saschpe.log4k

import saschpe.log4k.FileLogger.Rotate
import kotlin.test.Test
import kotlin.test.assertEquals

class MultipleLoggersTest {
    @Test
    fun log_multipleLoggers() {
        val before = Log.loggers
        Log.loggers.clear()
        Log.loggers += FileLogger(rotate = Rotate.Daily, logPath = TEST_LOG_PATH)
        Log.loggers += FileLogger(rotate = Rotate.After(40), logPath = TEST_LOG_PATH)

        assertEquals(2, Log.loggers.size)

        Log.debug { "Create them log files!" }

        // Clean up
        Log.loggers.clear()
        before.forEach { Log.loggers += it }
    }

    companion object {
        private val TEST_LOG_PATH = "build/test/${MultipleLoggersTest::class.simpleName}"
    }
}
