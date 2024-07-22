package shipments

class OvernightShipment(
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
) : Shipment(status, id, createdDateTimestamp, currentLocation, notes) {

    override fun validate() {
        if (createdDateTimestamp > expectedDeliveryDateTimestamp) {
            markInvalid("Delivery date is earlier than shipment date.")
        } else if (status != Status.Delayed && calculateDays(createdDateTimestamp, expectedDeliveryDateTimestamp) > 1) {
            markInvalid("Overnight shipment scheduled to arrive later than next-day.")
        } else {
            markValid()
        }
    }

}