package shipments

class BulkShipment(
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
) : Shipment(status, id, createdDateTimestamp, currentLocation, notes) {

    override fun validate() {
        if (calculateDays(createdDateTimestamp, expectedDeliveryDateTimestamp) < 3) {
            markInvalid("Bulk shipment scheduled to arrive in fewer than 3 days")
        } else if (status == Status.Invalid) {
            markValid()
        }
    }

}