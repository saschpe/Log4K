import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import saschpe.log4k.Log
import saschpe.log4k.slf4j.SLF4JLogger

fun main() = application {
    Log.loggers.clear()
    Log.loggers += SLF4JLogger()
    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")
    Log.debug { "Desktop main" }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Log4K Demo",
    ) {
        App()
    }
}
