package saschpe.log4k

import testing.*
import kotlin.test.*

class LogTest {
    @BeforeTest // Arrange
    fun before() {
        Log.loggers += TestLogger()
    }

    @Test
    fun verbose() {
        // Act
        Log.verbose(TEST_MESSAGE, TEST_THROWABLE, TEST_TAG)

        // Assert
        assertTestLogger(Log.Level.Verbose)
    }

    @Test
    fun verbose_withLambda() {
        // Act
        Log.verbose(TEST_THROWABLE, TEST_TAG) { TEST_MESSAGE }

        // Assert
        assertTestLogger(Log.Level.Verbose)
    }

    @Test
    fun info() {
        // Act
        Log.info(TEST_MESSAGE, TEST_THROWABLE, TEST_TAG)

        // Assert
        assertTestLogger(Log.Level.Info)
    }

    @Test
    fun info_withLambda() {
        // Act
        Log.info(TEST_THROWABLE, TEST_TAG) { TEST_MESSAGE }

        // Assert
        assertTestLogger(Log.Level.Info)
    }

    @Test
    fun debug() {
        // Act
        Log.debug(TEST_MESSAGE, TEST_THROWABLE, TEST_TAG)

        // Assert
        assertTestLogger(Log.Level.Debug)
    }

    @Test
    fun debug_withLambda() {
        // Act
        Log.debug(TEST_THROWABLE, TEST_TAG) { TEST_MESSAGE }

        // Assert
        assertTestLogger(Log.Level.Debug)
    }

    @Test
    fun warn() {
        // Act
        Log.warn(TEST_MESSAGE, TEST_THROWABLE, TEST_TAG)

        // Assert
        assertTestLogger(Log.Level.Warning)
    }

    @Test
    fun warn_withLambda() {
        // Act
        Log.warn(TEST_THROWABLE, TEST_TAG) { TEST_MESSAGE }

        // Assert
        assertTestLogger(Log.Level.Warning)
    }

    @Test
    fun error() {
        // Act
        Log.error(TEST_MESSAGE, TEST_THROWABLE, TEST_TAG)

        // Assert
        assertTestLogger(Log.Level.Error)
    }

    @Test
    fun error_withLambda() {
        // Act
        Log.error(TEST_THROWABLE, TEST_TAG) { TEST_MESSAGE }

        // Assert
        assertTestLogger(Log.Level.Error)
    }

    @Test
    fun assert() {
        // Act
        Log.assert(TEST_MESSAGE, TEST_THROWABLE, TEST_TAG)

        // Assert
        assertTestLogger(Log.Level.Assert)
    }

    @Test
    fun assert_withLambda() {
        // Act
        Log.assert(TEST_THROWABLE, TEST_TAG) { TEST_MESSAGE }

        // Assert
        assertTestLogger(Log.Level.Assert)
    }

    @Test
    fun logged_Pair() {
        // Arrange
        val pair = Pair("Hello", "World")

        // Act
        pair.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "(Hello, World)", "Pair", null)
    }

    @Test
    fun isDebugEnabled() {
        assertTrue(Log.isDebugEnabled, "TestLogger defaults to ${Log.Level.Verbose}")
        Log.loggers.clear()
        Log.loggers += StubLoggerWithMinimum(Log.Level.Warning)
        Log.loggers += StubLoggerWithMinimum(Log.Level.Info)
        assertFalse(Log.isDebugEnabled)
        Log.loggers += StubLoggerWithMinimum(Log.Level.Debug)
        assertTrue(Log.isDebugEnabled, "One of multiple logger has ${Log.Level.Debug} set")
        Log.loggers.clear()
        assertFalse(Log.isDebugEnabled)
    }

    @AfterTest
    fun after() = Log.loggers.clear()
}
