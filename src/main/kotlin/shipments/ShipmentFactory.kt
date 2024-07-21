package shipments

fun shipmentFactory(
    type: String,
    status: Status,
    id: String,
    createdDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
): Shipment =
    when (type) {
        "standard" ->  StandardShipment (status,id,createdDateTimestamp, currentLocation,notes)
        "bulk" ->      BulkShipment     (status,id,createdDateTimestamp, currentLocation,notes)
        "express" ->   ExpressShipment  (status,id,createdDateTimestamp, currentLocation,notes)
        "overnight" -> OvernightShipment(status,id,createdDateTimestamp, currentLocation,notes)
        else -> throw NotImplementedError("$type not a known shipment type")
    }