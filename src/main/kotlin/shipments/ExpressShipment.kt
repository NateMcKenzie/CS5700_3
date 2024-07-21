package shipments

class ExpressShipment(
    status: Status,
    id: String,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
) : Shipment(status, id, expectedDeliveryDateTimestamp, currentLocation, notes) {

    override fun validate() {
        TODO("Not yet implemented")
    }

}