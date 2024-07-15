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
        val foundShipment = TrackingSimulator.findShipment(id) ?: throw IllegalStateException("Shipment '$id' not found")
        foundShipment.subscribe(this)
        shipment = foundShipment
        update()
    }

    fun stopTracking(){
        shipment?.unsubscribe(this)
        shipment = null
        shipmentId = "No Longer Tracking"
    }

    override fun update() {
        // Credit to Claude AI for telling me how to handle this null in a kotlin way
        shipment?.let { observedShipment ->
            shipmentId = observedShipment.id
            shipmentStatus = observedShipment.status
            expectedShipmentDeliveryDate = observedShipment.expectedDeliveryDateTimestamp
            shipmentCurrentLocation = observedShipment.currentLocation

            //I tried adding each new note, but this led to problems when adding shipments that had existing histories
            //Probably a better solution available as each update is now O(n) instead of O(1)
            observedShipment.notes.forEach {
                if (!shipmentNotes.contains(it)){
                    shipmentNotes.add(it)
                }
            }
            observedShipment.updateHistory.forEach {
                if (!shipmentUpdateHistory.contains(it)){
                    shipmentUpdateHistory.add(it)
                }
            }
        }
    }
}
