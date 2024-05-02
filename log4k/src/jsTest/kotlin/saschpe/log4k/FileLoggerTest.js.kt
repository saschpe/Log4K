package saschpe.log4k

import node.fs.RmOptions
import node.fs.existsSync
import node.fs.readdirSync
import node.fs.rmSync

internal actual val expectedExceptionPackage: String = ""
internal actual val expectedTraceTag: String = "XXX"

@Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
internal actual fun deleteRecursively(path: String) = rmSync(path, js("{recursive: true, force: true}") as RmOptions)

internal actual fun filesInFolder(path: String): Int = when {
    existsSync(path) -> readdirSync(path).count()
    else -> 0
}
