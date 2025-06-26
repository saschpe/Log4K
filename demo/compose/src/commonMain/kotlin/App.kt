import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import log4k.demo.compose.generated.resources.Res
import log4k.demo.compose.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import saschpe.log4k.Log

@Composable
@Preview
fun App() {
    Log.debug { "Running composable 'App' with loggers: ${Log.loggers}" }
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        Column(
            Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click and log me!")
                Log.debug { "Button configured" }
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                    Log.verbose { "User interface rendered (again)" }
                }
            }
        }
    }
}
