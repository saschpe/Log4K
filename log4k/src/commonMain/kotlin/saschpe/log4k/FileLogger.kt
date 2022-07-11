package saschpe.log4k

import kotlinx.datetime.*
import kotlinx.datetime.format.char
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString

internal expect val defaultLogPath: Path

/**
 * Log to files in [logPath] with [rotate] rotation and imposed retention [limit].
 *
 * @param rotate Log file rotation, defaults to [Rotate.Daily]
 * @param limit Log file retention limit, defaults to [Limit.Files]
 * @param logPath Log file path, defaults to platform-specific temporary directory [defaultLogPath]
 */
class FileLogger(
    private val rotate: Rotate = Rotate.Daily,
    private val limit: Limit = Limit.Files(10),
    private val logPath: Path = defaultLogPath,
) : Logger() {
    constructor(
        rotation: Rotate = Rotate.Daily,
        limit: Limit = Limit.Files(),
        logPath: String,
    ) : this(rotation, limit, Path(logPath))

    init {
        SystemFileSystem.createDirectories(logPath)
    }

    override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        val logTag = tag.ifEmpty { getTraceTag() }
        SystemFileSystem.sink(rotate.logFile(logPath), append = true).buffered().apply {
            writeString("${level.name.first()}/$logTag: $message")
            throwable?.let { writeString(" $throwable") }
            writeString("\n")
            flush()
        }
        limit.enforce(logPath)
    }

    private fun getTraceTag(): String {
//        val trace = Exception().stackTrace[6]
//        val className = trace.className.split(".").last()
//        return "$className.${trace.methodName}"
        return "XXX"
    }

    /**
     * Log file rotation.
     */
    sealed class Rotate {
        internal abstract fun logFile(logPath: Path): Path
        internal abstract fun newLine()

        /**
         * Never rotate the log file.
         *
         * Use with caution, may lead to a single huge log file.
         * Subject to the platforms temporary directory cleanup policy.
         */
        data object Never : Rotate() {
            override fun logFile(logPath: Path) = Path(logPath, "log.txt")
            override fun newLine() = Unit
        }

        /**
         * Daily log rotation.
         */
        data object Daily : Rotate() {
            override fun logFile(logPath: Path): Path {
                val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
                return Path(logPath, "log.daily.${LocalDate.Formats.ISO.format(today)}.txt")
            }

            override fun newLine() = Unit
        }

        /**
         * Rotate the current log file after X number of lines written.
         */
        class After(private val lines: Int = 10000) : Rotate() {
            private val dateTimeFormat = LocalDateTime.Format {
                year()
                monthNumber()
                dayOfMonth()
                char('-')
                hour()
                minute()
                second()
            }
            private var linesWritten = 0

            override fun logFile(logPath: Path): Path {
                val logFile = Path(logPath, "log.after.txt")
                if (SystemFileSystem.exists(logFile) && linesWritten > lines) {
                    val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                    val renameTo = Path(logPath, "log.after.${dateTimeFormat.format(now)}.txt")
                    SystemFileSystem.atomicMove(logFile, renameTo)
                    linesWritten = 0
                }
                return logFile
            }

            override fun newLine() {
                linesWritten.inc()
            }
        }
    }

    /**
     * Log file storage limit.
     */
    sealed class Limit {
        internal abstract fun enforce(logPath: Path)

        /**
         * There's no limit!
         */
        data object Not : Limit() {
            override fun enforce(logPath: Path) = Unit
        }

        /**
         * Keep [max] log files in the [logPath].
         *
         * @param max Number of files to keep, defaults to 10
         */
        class Files(private val max: Int = 10) : Limit() {
            override fun enforce(logPath: Path) {
                // TODO: Go native!
            }
        }

        /**
         * Keep [max] megabytes worth of files in the [logPath].
         *
         * @param max Megabytes worth keeping, defaults to 10MB
         */
        class MegaBytes(private val max: Int = 10) : Limit() {
            override fun enforce(logPath: Path) {
                // TODO: Go native!
            }
        }
    }
}
