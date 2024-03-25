package saschpe.log4k

import kotlinx.io.files.Path
import saschpe.log4k.internal.ContextProvider.Companion.applicationContext

internal actual val defaultLogPath: Path
    get() = Path(applicationContext.cacheDir.path)
