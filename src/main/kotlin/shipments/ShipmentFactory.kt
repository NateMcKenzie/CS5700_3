package shipments

fun shipmentFactory(
    type: String,
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
): Shipment =
    when (type) {
        "standard" ->  StandardShipment (status,id,createdDateTimestamp, expectedDeliveryDateTimestamp,currentLocation,notes)
        "bulk" ->      BulkShipment     (status,id,createdDateTimestamp, expectedDeliveryDateTimestamp,currentLocation,notes)
        "express" ->   ExpressShipment  (status,id,createdDateTimestamp, expectedDeliveryDateTimestamp,currentLocation,notes)
        "overnight" -> OvernightShipment(status,id,createdDateTimestamp, expectedDeliveryDateTimestamp,currentLocation,notes)
        else -> throw NotImplementedError("$type not a known shipment type")
    }