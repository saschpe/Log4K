package saschpe.log4k

import kotlinx.io.files.Path
import saschpe.log4k.internal.ContextProvider.Companion.applicationContext
import java.io.File

internal actual val defaultLogPath: Path
    get() = Path(applicationContext.cacheDir.path)

internal actual fun limitFolderToFilesByCreationTime(path: String, limit: Int) {
    File(path).listFiles()?.sorted()?.run {
        val overhead = kotlin.math.max(0, count() - limit)
        take(overhead).forEach { it.delete() }
    }
}
