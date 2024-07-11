import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import saschpe.log4k.FileLogger
import saschpe.log4k.FileLogger.Limit
import saschpe.log4k.FileLogger.Rotate
import saschpe.log4k.Log

fun main() = application {
    Log.loggers += FileLogger(rotate = Rotate.Daily, limit = Limit.Files(max = 5))
    Log.debug { "Desktop main" }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Log4K Demo",
    ) {
        App()
    }
}
