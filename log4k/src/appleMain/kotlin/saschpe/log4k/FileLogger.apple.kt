package saschpe.log4k

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import platform.Foundation.NSFileManager
import kotlin.math.max

internal actual val defaultLogPath: Path
    get() = SystemTemporaryDirectory

@OptIn(ExperimentalForeignApi::class)
internal actual fun limitFolderToFilesByCreationTime(path: String, limit: Int) {
    val fm = NSFileManager.defaultManager

    @Suppress("UNCHECKED_CAST")
    val files = fm.contentsOfDirectoryAtPath(path, null) as List<String>?
    // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): files=$files")

    files?.sorted()?.run {
        val overhead = max(0, size - limit)
        // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): overhead=$overhead")
        take(overhead).forEach {
            // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): remove $it")
            fm.removeItemAtPath("$path/$it", null)
        }
    }
}
