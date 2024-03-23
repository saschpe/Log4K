package testing

import saschpe.log4k.Log
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class TestLoggerTest {
    @BeforeTest // Arrange
    fun before() {
        Log.loggers.clear()
        Log.loggers += TestLogger()
    }

    @AfterTest
    fun after() = Log.loggers.clear()
}
