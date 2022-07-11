package saschpe.log4k

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSFileManager

internal actual val expectedExceptionPackage: String = "kotlin."
internal actual val expectedTraceTag: String = "XXX"

@OptIn(ExperimentalForeignApi::class)
internal actual fun deleteRecursively(path: String) {
    NSFileManager.defaultManager.removeItemAtPath(path, error = null)
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun filesInFolder(path: String): Int =
    NSFileManager.defaultManager.contentsOfDirectoryAtPath(path, null)?.count() ?: 0
