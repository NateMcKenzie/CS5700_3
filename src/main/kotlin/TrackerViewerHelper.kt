import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TrackerViewerHelper: Observer {
    var shipmentId by mutableStateOf("")
        private set
    var shipmentStatus by mutableStateOf("")
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
    }

    fun stopTracking(){
        shipment?.unsubscribe()
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