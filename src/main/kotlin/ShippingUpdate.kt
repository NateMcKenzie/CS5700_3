class ShippingUpdate (
    shipment: Shipment,
    val timestamp: Long,
    val newStatus: String = shipment.status,
    val newDeliveryDate: Long = shipment.expectedDeliveryDateTimestamp,
    val newLocation: String = shipment.currentLocation
){
    val previousStatus = shipment.status
    val previousDeliveryDate = shipment.expectedDeliveryDateTimestamp
    val previousLocation = shipment.currentLocation
}