import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import saschpe.log4k.Log

@Suppress("unused") // Used on iOS
fun mainViewController(): UIViewController {
    Log.debug { "XX1" }
    return ComposeUIViewController {
        Log.debug { "XX2" }
        App()
    }
}
