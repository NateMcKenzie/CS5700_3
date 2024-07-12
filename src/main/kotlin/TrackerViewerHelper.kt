import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TrackerViewerHelper: Observer {
    var shipmentId by mutableStateOf("")
        private set
    var shipmentStatus by mutableStateOf(Status.Unknown)
        private set
    //TODO: Figure out how to do lists and fix update history
    var shipmentUpdateHistory by mutableStateOf("")
        private set
    var expectedShipmentDeliveryDate by mutableStateOf(0L)
        private set
    var shipmentCurrentLocation by mutableStateOf("")
        private set
    var shipment: Shipment? = null
        private set

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
            //shipmentUpdateHistory = currentShipment.updateHistory
            expectedShipmentDeliveryDate = currentShipment.expectedDeliveryDateTimestamp
            shipmentCurrentLocation = currentShipment.currentLocation
        }
    }
}
