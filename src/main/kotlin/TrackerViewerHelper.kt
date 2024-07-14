import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList

class TrackerViewerHelper: Observer {
    var shipmentId by mutableStateOf("")
        private set
    var shipmentStatus by mutableStateOf(Status.Unknown)
        private set
    var shipmentUpdateHistory = mutableStateListOf<ShippingUpdate>()
        private set
    var expectedShipmentDeliveryDate by mutableStateOf(0L)
        private set
    var shipmentCurrentLocation by mutableStateOf("")
        private set
    var shipmentNotes = mutableStateListOf<String>()
    private var shipment: Shipment? = null

    fun trackShipment(id: String){
        // Credit to Claude AI for telling me how to handle this null in a kotlin way
        val foundShipment = TrackingSimulator.findShipment(id) ?: return
        foundShipment.subscribe(this)
        shipment = foundShipment
        update()
    }

    fun stopTracking(){
        shipment?.unsubscribe(this)
        shipment = null
    }

    override fun update() {
        // Credit to Claude AI for telling me how to handle this null in a kotlin way
        shipment?.let { currentShipment ->
            shipmentId = currentShipment.id
            shipmentStatus = currentShipment.status
            expectedShipmentDeliveryDate = currentShipment.expectedDeliveryDateTimestamp
            shipmentCurrentLocation = currentShipment.currentLocation
            if (currentShipment.notes.isNotEmpty() && (shipmentNotes.isEmpty() || currentShipment.notes.last() != shipmentNotes.last()))
            {
                shipmentNotes.add(currentShipment.notes.last())
            }
            if (currentShipment.updateHistory.isNotEmpty() && (shipmentUpdateHistory.isEmpty() || currentShipment.updateHistory.last() != shipmentUpdateHistory.last()))
            {
                shipmentUpdateHistory.add(currentShipment.updateHistory.last())
            }
        }
    }
}
