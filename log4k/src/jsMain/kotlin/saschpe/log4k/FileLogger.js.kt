package saschpe.log4k

import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory
import node.fs.RmOptions
import node.fs.readdirSync
import node.fs.rmSync

internal actual val defaultLogPath: Path
    get() = SystemTemporaryDirectory

internal actual fun limitFolderToFilesByCreationTime(path: String, limit: Int) {
    readdirSync(path).sorted().run {
        val overhead = kotlin.math.max(0, count() - limit)
        // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): count=${count()}, overhead=$overhead")
        take(overhead).forEach {
            val file = "$path/$it"
            // println("limitFolderToFilesByCreationTime(path=$path, limit=$limit): remove $file")
            @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
            rmSync(file, js("{force: true}") as RmOptions)
        }
    }
}
