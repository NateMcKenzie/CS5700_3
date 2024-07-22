package shipments

class StandardShipment(
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
) : Shipment(status, id, createdDateTimestamp, currentLocation, notes) {

    override fun validate() {
        if (calculateDays(createdDateTimestamp, expectedDeliveryDateTimestamp) < 0){
            markInvalid("Delivery date is earlier than shipment date.")
        }
    }

}