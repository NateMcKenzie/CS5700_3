package shipments

fun shipmentFactory(
    type: String,
    status: Status,
    id: String,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
): Shipment =
    when (type) {
        "standard" -> StandardShipment(status,id,expectedDeliveryDateTimestamp,currentLocation,notes)
        "bulk" -> BulkShipment(status,id,expectedDeliveryDateTimestamp,currentLocation,notes)
        "express" -> ExpressShipment(status,id,expectedDeliveryDateTimestamp,currentLocation,notes)
        "overnight" -> OvernightShipment(status,id,expectedDeliveryDateTimestamp,currentLocation,notes)
        else -> throw NotImplementedError("$type not a known shipment type")
    }