package saschpe.log4k

import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import java.io.File
import kotlin.math.max

internal actual val defaultLogPath: Path
    get() = SystemTemporaryDirectory

internal actual fun limitFolderToFilesByCreationTime(path: String, limit: Int) {
    File(path).listFiles()?.sorted()?.run {
        val overhead = max(0, count() - limit)
        // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): count=${count()}, overhead=$overhead")
        take(overhead).forEach {
            // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): remove $it")
            it.delete()
        }
    }
}
