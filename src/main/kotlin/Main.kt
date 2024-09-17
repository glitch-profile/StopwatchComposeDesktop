import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import domain.Stopwatch
import presentation.StopwatchScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val stopwatch = remember { Stopwatch() }
            val formattedTimeString = stopwatch.formattedTimeString.collectAsState()

            StopwatchScreen(
                modifier = Modifier
                    .align(Alignment.Center),
                formattedTime = formattedTimeString.value,
                onStartClick = stopwatch::start,
                onPauseClick = stopwatch::pause,
                onResetClicked = stopwatch::reset
            )
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
