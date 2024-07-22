import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import shipments.Shipment
import shipments.Status

class TrackerViewerHelper : Observer {
    var shipmentId by mutableStateOf("")
        private set
    var shipmentStatus by mutableStateOf(Status.Unknown)
        private set
    var shipmentUpdateHistory = mutableStateListOf<ShippingUpdate>()
        private set
    var shipmentCreatedDate by mutableStateOf(0L)
        private set
    var expectedShipmentDeliveryDate by mutableStateOf(0L)
        private set
    var shipmentCurrentLocation by mutableStateOf("")
        private set
    var shipmentNotes = mutableStateListOf<String>()
    var shipmentInvalidReason by mutableStateOf("")
    private var shipment: Shipment? = null

    fun trackShipment(id: String): Boolean {
        // Credit to Claude AI for telling me how to handle this null in a kotlin way
        val foundShipment = TrackingManager.findShipment(id) ?: return false
        foundShipment.subscribe(this)
        shipment = foundShipment
        update()
        return true
    }

    fun stopTracking() {
        shipment?.unsubscribe(this)
        shipment = null
        shipmentId = "No Longer Tracking"
    }

    override fun update() {
        // Credit to Claude AI for telling me how to handle this null in a kotlin way
        shipment?.let { observedShipment ->
            shipmentId = observedShipment.id
            shipmentStatus = observedShipment.status
            shipmentCreatedDate = observedShipment.createdDateTimestamp
            expectedShipmentDeliveryDate = observedShipment.expectedDeliveryDateTimestamp
            shipmentCurrentLocation = observedShipment.currentLocation
            shipmentInvalidReason = observedShipment.invalidReason

            observedShipment.notes.forEach {
                if (!shipmentNotes.contains(it)) {
                    shipmentNotes.add(it)
                }
            }
            observedShipment.updateHistory.forEach {
                if (!shipmentUpdateHistory.contains(it)) {
                    shipmentUpdateHistory.add(it)
                }
            }
        }
    }
}
