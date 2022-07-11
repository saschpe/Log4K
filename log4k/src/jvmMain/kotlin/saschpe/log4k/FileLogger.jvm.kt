package saschpe.log4k

import kotlinx.io.files.Path
import kotlinx.io.files.SystemTemporaryDirectory

internal actual val defaultLogPath: Path
    get() = SystemTemporaryDirectory
