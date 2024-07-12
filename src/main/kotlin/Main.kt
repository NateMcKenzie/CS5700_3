import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var shipmentHelpers = remember { mutableStateListOf<TrackerViewerHelper>() }
    var idInput by remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            Row {
                TextField(idInput, onValueChange = {
                    idInput = it
                })
                Button(onClick = {
                    val newHelper = TrackerViewerHelper()
                    newHelper.trackShipment(idInput)
                    shipmentHelpers.add(newHelper)
                }) {
                }
            }
            LazyColumn {
                items(shipmentHelpers, key = {it.shipmentId}) {
                        Text(it.shipmentCurrentLocation)
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
        TrackingSimulator.runSimulation("res/provided.txt")
    }
}
