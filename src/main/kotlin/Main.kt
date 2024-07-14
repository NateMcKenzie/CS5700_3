import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
@Preview
fun App() {
    var shipmentHelpers = remember { mutableStateListOf<TrackerViewerHelper>() }
    var idInput by remember { mutableStateOf("") }
    val heading = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    val body = TextStyle(color = Color.White)

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
                    Card (
                            modifier = Modifier.padding(3.dp).
                            border(2.dp, Color.Black).
                            background(Color.DarkGray)
                        ) {
                            Column (modifier = Modifier.padding(3.dp).background(Color.DarkGray)){
                                Text(it.shipmentId, style = heading, modifier = Modifier.padding(2.dp))
                                Text(it.shipmentStatus.toString(), style = body, modifier = Modifier.padding(2.dp))
                                Text(it.shipmentCurrentLocation, style = body, modifier = Modifier.padding(2.dp))
                                Text(stampConvert(it.expectedShipmentDeliveryDate), style = body, modifier = Modifier.padding(2.dp))
                                LazyRow {
                                    items(it.shipmentNotes, key = { it }) {
                                        Text(it, style = body, modifier = Modifier.padding(2.dp))
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
}

fun stampConvert(timestamp: Long) : String{
    val instant = Instant.ofEpochMilli(timestamp)
    return instant.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mma"))
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
        TrackingSimulator.runSimulation("res/provided.txt")
    }
}
