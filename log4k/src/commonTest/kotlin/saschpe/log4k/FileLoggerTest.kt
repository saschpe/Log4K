package saschpe.log4k

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readString
import saschpe.log4k.FileLogger.Rotate
import kotlin.test.*

internal expect val exceptionPackageName: String

class FileLoggerTest {
    @BeforeTest
    fun beforePaths() {
        SystemFileSystem.delete(TEST_DAILY_ROTATION_LOG_FILE, mustExist = false)
    }

    @Test
    fun rotate_daily_logFileName() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val expectedFileName = "log.daily.${LocalDate.Formats.ISO.format(today)}.txt"
        assertEquals(expectedFileName, TEST_DAILY_ROTATION_LOG_FILE.name, "Like 'log.daily.2024-03-25.txt")
        assertFalse { TEST_DAILY_ROTATION_LOG_FILE.isAbsolute }
    }

    @Test
    fun rotate_after_logFileName() {
        assertEquals("log.after.txt", TEST_AFTER_ROTATION_LOG_FILE.name)
        assertFalse { TEST_AFTER_ROTATION_LOG_FILE.isAbsolute }
    }

    @Test
    fun log_rotate_daily() {
        // Arrange
        val logger = FileLogger(rotate = Rotate.Daily, logPath = TEST_LOG_PATH)

        // Act
        logger.log(Log.Level.Verbose, "TAG1", "Verbose message", null)
        logger.log(Log.Level.Debug, "TAG1", "Debug message", Exception("Test exception!"))
        logger.log(Log.Level.Info, "TAG2", "Info message", null)
        logger.log(Log.Level.Warning, "TAG3", "Warning message", IllegalStateException("Illegal test state!"))
        logger.log(Log.Level.Error, "TAG1", "Error message", null)
        logger.log(Log.Level.Assert, "TAG1", "Assert message", null)

        // Assert
        assertTrue { SystemFileSystem.metadataOrNull(TEST_LOG_PATH)?.isDirectory ?: false }
        assertTrue { SystemFileSystem.exists(TEST_DAILY_ROTATION_LOG_FILE) }
        assertTrue { SystemFileSystem.metadataOrNull(TEST_DAILY_ROTATION_LOG_FILE)?.isRegularFile ?: false }
        assertEquals(TEST_LOG_CONTENT, SystemFileSystem.source(TEST_DAILY_ROTATION_LOG_FILE).buffered().readString())
    }

    @Test
    fun log_rotate_after_10_lines() {
        // Arrange
        val logger = FileLogger(rotation = Rotate.After(lines = 10), logPath = TEST_LOG_PATH_STRING)

        // Act
        logger.log(Log.Level.Verbose, "TAG1", "Verbose message", null)
        logger.log(Log.Level.Debug, "TAG1", "Debug message", Exception("Test exception!"))
        logger.log(Log.Level.Info, "TAG2", "Info message", null)
        logger.log(Log.Level.Warning, "TAG3", "Warning message", IllegalStateException("Illegal test state!"))
        logger.log(Log.Level.Error, "TAG1", "Error message", null)
        logger.log(Log.Level.Assert, "TAG1", "Assert message", null)

        // Assert
        assertTrue { SystemFileSystem.metadataOrNull(TEST_LOG_PATH)?.isDirectory ?: false }
        assertTrue { SystemFileSystem.exists(TEST_DAILY_ROTATION_LOG_FILE) }
        assertTrue { SystemFileSystem.metadataOrNull(TEST_DAILY_ROTATION_LOG_FILE)?.isRegularFile ?: false }
        assertEquals(TEST_LOG_CONTENT, SystemFileSystem.source(TEST_AFTER_ROTATION_LOG_FILE).buffered().readString())
    }

    @Test
    fun log_rotate_after_5_lines() {
        // Arrange
        val logger = FileLogger(rotate = Rotate.After(lines = 10), logPath = TEST_LOG_PATH)

        // Act
        logger.log(Log.Level.Verbose, "TAG1", "Verbose message", null)
        logger.log(Log.Level.Debug, "TAG1", "Debug message", Exception("Test exception!"))
        logger.log(Log.Level.Info, "TAG2", "Info message", null)
        logger.log(Log.Level.Warning, "TAG3", "Warning message", IllegalStateException("Illegal test state!"))
        logger.log(Log.Level.Error, "TAG1", "Error message", null)
        logger.log(Log.Level.Assert, "TAG1", "Assert message", null)

        // Assert
        assertTrue { SystemFileSystem.metadataOrNull(TEST_LOG_PATH)?.isDirectory ?: false }
        assertTrue { SystemFileSystem.exists(TEST_DAILY_ROTATION_LOG_FILE) }
        assertTrue { SystemFileSystem.metadataOrNull(TEST_DAILY_ROTATION_LOG_FILE)?.isRegularFile ?: false }
        assertEquals(TEST_LOG_CONTENT, SystemFileSystem.source(TEST_AFTER_ROTATION_LOG_FILE).buffered().readString())
    }

    companion object {
        private val TEST_LOG_PATH_STRING = "build/${FileLogger::class.simpleName}"
        private val TEST_LOG_PATH = Path(TEST_LOG_PATH_STRING)
        private val TEST_DAILY_ROTATION_LOG_FILE = Rotate.Daily.logFile(TEST_LOG_PATH)
        private val TEST_AFTER_ROTATION_LOG_FILE = Rotate.After(100).logFile(TEST_LOG_PATH)
        private val TEST_LOG_CONTENT = """
        V/TAG1: Verbose message
        D/TAG1: Debug message ${exceptionPackageName}Exception: Test exception!
        I/TAG2: Info message
        W/TAG3: Warning message ${exceptionPackageName}IllegalStateException: Illegal test state!
        E/TAG1: Error message
        A/TAG1: Assert message

        """.trimIndent()
    }
}
