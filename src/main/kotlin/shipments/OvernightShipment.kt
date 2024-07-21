package shipments

class OvernightShipment(
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
) : Shipment(status, id, createdDateTimestamp, currentLocation, notes) {

    override fun validate() {
        if(calculateDays(createdDateTimestamp, expectedDeliveryDateTimestamp) > 0){
            markInvalid("Overnight shipment scheduled to arrive in more than 0 days")
        }
    }

}