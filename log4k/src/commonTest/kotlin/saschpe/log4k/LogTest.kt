package saschpe.log4k

import testing.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

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

    @AfterTest
    fun after() = Log.loggers.clear()
}
