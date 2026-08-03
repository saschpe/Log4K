package saschpe.log4k

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import org.junit.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import android.util.Log as AndroidLog

class AndroidConsoleLoggerTest {
    @BeforeTest
    fun beforeMocks() {
        assertEquals(1, Log.loggers.size)
        assertTrue { Log.loggers.first() is ConsoleLogger }

        mockkStatic(AndroidLog::class)
    }

    @Test
    fun verbose() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        every { AndroidLog.v(capture(tagSlot), capture(msgSlot), any()) } returns 1

        // Act
        Log.verbose { "Hello" }

        // Assert
        assertEquals("AndroidConsoleLoggerTest.verbose", tagSlot.captured)
        assertEquals("Hello", msgSlot.captured)
    }

    @Test
    fun debug() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        every { AndroidLog.d(capture(tagSlot), capture(msgSlot), any()) } returns 1

        // Act
        Log.debug { "Hello" }

        // Assert
        assertEquals("AndroidConsoleLoggerTest.debug", tagSlot.captured)
        assertEquals("Hello", msgSlot.captured)
    }

    @Test
    fun info() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        every { AndroidLog.i(capture(tagSlot), capture(msgSlot), any()) } returns 1

        // Act
        Log.info { "Hello" }

        // Assert
        assertEquals("AndroidConsoleLoggerTest.info", tagSlot.captured)
        assertEquals("Hello", msgSlot.captured)
    }

    @Test
    fun warn() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        every { AndroidLog.w(capture(tagSlot), capture(msgSlot), any()) } returns 1

        // Act
        Log.warn { "Hello" }

        // Assert
        assertEquals("AndroidConsoleLoggerTest.warn", tagSlot.captured)
        assertEquals("Hello", msgSlot.captured)
    }

    @Test
    fun error() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        every { AndroidLog.e(capture(tagSlot), capture(msgSlot), any()) } returns 1

        // Act
        Log.error { "Hello" }

        // Assert
        assertEquals("AndroidConsoleLoggerTest.error", tagSlot.captured)
        assertEquals("Hello", msgSlot.captured)
    }

    @Test
    fun assert_withException() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        val exceptionSlot = slot<Throwable>()
        val exception = Exception("World")
        every { AndroidLog.wtf(capture(tagSlot), capture(msgSlot), capture(exceptionSlot)) } returns 1

        // Act
        Log.assert(exception) { "Hello" }

        // Assert
        assertEquals("AndroidConsoleLoggerTest.assert_withException", tagSlot.captured)
        assertEquals("Hello", msgSlot.captured)
        assertEquals(exception, exceptionSlot.captured)
    }

    @Test
    fun log() {
        // Arrange
        val tagSlot = slot<String>()
        val msgSlot = slot<String>()
        every { AndroidLog.wtf(capture(tagSlot), capture(msgSlot), any()) } returns 1

        // Act
        Log.log(Log.Level.Assert, message = "World")

        // Assert
        // TODO: assertEquals("AndroidConsoleLoggerTest.log", tagSlot.captured)
        assertEquals("World", msgSlot.captured)
    }

    @AfterTest
    fun afterMocks() = unmockkAll()
}
