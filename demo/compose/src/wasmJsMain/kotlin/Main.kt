import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
//    Log.loggers += FileLogger(rotate = Rotate.Daily, limit = Limit.Files(max = 5))

    ComposeViewport(document.body!!) {
        App()
    }
}
