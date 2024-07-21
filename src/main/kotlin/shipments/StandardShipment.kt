package shipments

class StandardShipment(
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf()
) : Shipment(status, id, createdDateTimestamp, expectedDeliveryDateTimestamp, currentLocation, notes) {

    override fun validate() {}

}