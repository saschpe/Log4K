package saschpe.log4k

import java.io.File

internal actual val expectedExceptionPackage: String = "java.lang."
internal actual val expectedTraceTag: String = "FileLoggerTest.testMessages"

internal actual fun deleteRecursively(path: String) {
    File(path).deleteRecursively()
}

internal actual fun filesInFolder(path: String): Int = File(path).listFiles()?.size ?: 0
