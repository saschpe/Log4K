package saschpe.log4k.demo

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import saschpe.log4k.FileLogger
import saschpe.log4k.FileLogger.Limit
import saschpe.log4k.FileLogger.Rotate
import saschpe.log4k.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Move to application subclass in a real-world app:
        Log.loggers += FileLogger(rotate = Rotate.Daily, limit = Limit.Files(max = 5))

        Log.debug { "Android MainActivity.onCreate" }
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
