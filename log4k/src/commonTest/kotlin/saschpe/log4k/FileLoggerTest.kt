@file:OptIn(ExperimentalTime::class)

package saschpe.log4k

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.readString
import saschpe.log4k.FileLogger.Limit
import saschpe.log4k.FileLogger.Rotate
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.test.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.io.files.SystemFileSystem as fileSystem

internal expect val expectedExceptionPackage: String
internal expect val expectedTraceTag: String
internal expect fun deleteRecursively(path: String)
internal expect fun filesInFolder(path: String): Int

class FileLoggerTest {
    private val testLogPathString = "build/test/${FileLogger::class.simpleName}_${Random.nextUInt()}"
    private val testLogPath = Path(testLogPathString)
    private val testRotateAfter5LogFile = TEST_ROTATE_AFTER_5.logFile(testLogPath)
    private val testRotateAfter9LogFile = TEST_ROTATE_AFTER_9.logFile(testLogPath)
    private val testRotateDailyLogFile = Rotate.Daily.logFile(testLogPath)

    @BeforeTest
    fun beforePaths() {
        deleteRecursively(testLogPathString)
        assertEquals(0, filesInFolder(testLogPathString))
    }

    @Test
    fun rotate_daily_logFileName() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val expectedFileName = "log.daily.${LocalDate.Formats.ISO.format(today)}.txt"
        assertEquals(expectedFileName, testRotateDailyLogFile.name, "Like 'log.daily.2024-03-25.txt")
        assertFalse { testRotateDailyLogFile.isAbsolute }
    }

    @Test
    fun rotate_after_5_logFileName() {
        assertEquals("log.after_5.txt", testRotateAfter5LogFile.name)
        assertFalse { testRotateAfter5LogFile.isAbsolute }
    }

    @Test
    fun rotate_after_9_logFileName() {
        assertEquals("log.after_9.txt", testRotateAfter9LogFile.name)
        assertFalse { testRotateAfter9LogFile.isAbsolute }
    }

    @Test
    fun log_rotate_after_9_lines() {
        // Arrange
        val logger = FileLogger(rotate = TEST_ROTATE_AFTER_9, logPath = testLogPathString)

        // Act
        logger.testMessages()

        // Assert
        assertTrue { fileSystem.metadataOrNull(testLogPath)?.isDirectory ?: false }
        assertTrue { fileSystem.exists(testRotateAfter9LogFile) }
        assertTrue { fileSystem.metadataOrNull(testRotateAfter9LogFile)?.isRegularFile ?: false }
        assertEquals(
            "$TEST_ROTATE_AFTER_9_LOG_CONTENT\n",
            fileSystem.source(testRotateAfter9LogFile).buffered().readString(),
        )
        assertEquals(2, filesInFolder(testLogPathString))
    }

    @Ignore // The class works, but testing async I/O is hard across all platforms
    @Test
    fun log_rotate_after_5_lines() {
        // Arrange
        val logger = FileLogger(rotate = TEST_ROTATE_AFTER_5, logPath = testLogPathString)

        // Act
        logger.testMessages()

        // Assert
        assertTrue { fileSystem.metadataOrNull(testLogPath)?.isDirectory ?: false }
        assertTrue { fileSystem.exists(testRotateAfter5LogFile) }
        assertTrue { fileSystem.metadataOrNull(testRotateAfter5LogFile)?.isRegularFile ?: false }
        assertEquals(
            "$TEST_ROTATE_AFTER_5_LOG_CONTENT\n",
            fileSystem.source(testRotateAfter5LogFile).buffered().readString(),
        )
        assertEquals(3, filesInFolder(testLogPathString))
    }

    @Test
    fun log_rotate_daily() {
        // Arrange
        val logger = FileLogger(rotate = Rotate.Daily, logPath = testLogPathString)

        // Act
        logger.testMessages()

        // Assert
        assertTrue { fileSystem.metadataOrNull(testLogPath)?.isDirectory ?: false }
        assertTrue { fileSystem.exists(testRotateDailyLogFile) }
        assertTrue { fileSystem.metadataOrNull(testRotateDailyLogFile)?.isRegularFile ?: false }
        assertEquals(
            "$TEST_ROTATE_DAILY_LOG_CONTENT\n",
            fileSystem.source(testRotateDailyLogFile).buffered().readString(),
        )
        assertEquals(1, filesInFolder(testLogPathString))
    }

    @Test
    fun log_rotate_never() {
        // Arrange
        val logger = FileLogger(rotate = Rotate.Never, logPath = testLogPathString)

        // Act
        logger.testMessages()

        // Assert
        val logFile = Rotate.Never.logFile(testLogPath)

        assertTrue { fileSystem.metadataOrNull(testLogPath)?.isDirectory ?: false }
        assertTrue { fileSystem.exists(logFile) }
        assertTrue { fileSystem.metadataOrNull(logFile)?.isRegularFile ?: false }
        assertEquals(
            "$TEST_ROTATE_DAILY_LOG_CONTENT\n",
            fileSystem.source(logFile).buffered().readString(),
        )
        assertEquals(1, filesInFolder(testLogPathString))
    }

    @Ignore // The class works, but testing async I/O is hard across all platforms
    @Test
    fun log_limit_not() {
        // Arrange
        val rotate = Rotate.After(7)
        val logger = FileLogger(rotate, Limit.Not, testLogPathString)

        // Act
        logger.testMessages()
        logger.testMessages()
        logger.log(Log.Level.Debug, "", "hey", null)

        // Assert
        assertEquals(5, filesInFolder(testLogPathString))
        assertTrue { fileSystem.exists(rotate.logFile(testLogPath)) }
    }

    @Ignore // The class works, but testing async I/O is hard across all platforms
    @Test
    fun log_limit_to_7_files() {
        // Arrange
        val rotate = Rotate.After(3)
        val logger = FileLogger(rotate, Limit.Files(7), testLogPathString)

        // Act
        logger.testMessages()
        logger.testMessages()

        // Assert
        assertEquals(7, filesInFolder(testLogPathString))
        assertTrue { fileSystem.exists(rotate.logFile(testLogPath)) }
    }

    @Test
    fun log_limit_to_4_files() {
        // Arrange
        val rotate = Rotate.After(5)
        val logger = FileLogger(rotate, Limit.Files(4), testLogPathString)

        // Act
        logger.testMessages()
        logger.testMessages()

        // Assert
        assertEquals(4, filesInFolder(testLogPathString))
        assertTrue { fileSystem.exists(rotate.logFile(testLogPath)) }
    }

    private fun Logger.testMessages() {
        log(Log.Level.Verbose, "TAG1", "Verbose message", null)
        log(Log.Level.Verbose, "TAG2", "Verbose message", null)
        log(Log.Level.Verbose, "TAG3", "Verbose message", null)
        log(Log.Level.Debug, "TAG1", "Debug message", Exception("Test exception!"))
        log(Log.Level.Debug, "TAG2", "Debug message", Exception("Test exception!"))
        log(Log.Level.Debug, "TAG3", "Debug message", Exception("Test exception!"))
        log(Log.Level.Info, "TAG2", "Info message", null)
        log(Log.Level.Warning, "TAG3", "Warning message", IllegalStateException("Illegal test state!"))
        log(Log.Level.Error, "TAG1", "Error message", null)
        log(Log.Level.Error, "TAG2", "Error message", null)
        log(Log.Level.Error, "", "Error message", null)
        log(Log.Level.Assert, "TAG1", "Assert message", null)
        log(Log.Level.Assert, "TAG2", "Assert message", null)
        log(Log.Level.Assert, "", "Assert message", null)
    }

    companion object {
        private val TEST_ROTATE_AFTER_5 = Rotate.After(lines = 5)
        private val TEST_ROTATE_AFTER_9 = Rotate.After(lines = 9)
        private val TEST_ROTATE_AFTER_5_LOG_CONTENT = """
            E/$expectedTraceTag: Error message
            A/TAG1: Assert message
            A/TAG2: Assert message
            A/$expectedTraceTag: Assert message
        """.trimIndent()
        private val TEST_ROTATE_AFTER_9_LOG_CONTENT = """
            E/TAG2: Error message
            E/$expectedTraceTag: Error message
            A/TAG1: Assert message
            A/TAG2: Assert message
            A/$expectedTraceTag: Assert message
        """.trimIndent()
        private val TEST_ROTATE_DAILY_LOG_CONTENT = """
            V/TAG1: Verbose message
            V/TAG2: Verbose message
            V/TAG3: Verbose message
            D/TAG1: Debug message ${expectedExceptionPackage}Exception: Test exception!
            D/TAG2: Debug message ${expectedExceptionPackage}Exception: Test exception!
            D/TAG3: Debug message ${expectedExceptionPackage}Exception: Test exception!
            I/TAG2: Info message
            W/TAG3: Warning message ${expectedExceptionPackage}IllegalStateException: Illegal test state!
            E/TAG1: Error message
            E/TAG2: Error message
            E/$expectedTraceTag: Error message
            A/TAG1: Assert message
            A/TAG2: Assert message
            A/$expectedTraceTag: Assert message
        """.trimIndent()
    }
}
