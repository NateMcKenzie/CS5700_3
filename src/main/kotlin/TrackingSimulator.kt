import InstructionHandlers.*

class TrackingSimulator {
    val shipments = mutableListOf<Shipment>();
    val instructionMap = mapOf<String, InstructionHandler>(
        "created" to CreateShipment(),
        "shipped" to Ship(),
        "location" to UpdateLocation(),
        "delayed" to Delay(),
        "noteadded" to AddNote(),
        "lost" to Lose(),
        "canceled" to Cancel(),
        "delivered" to Deliver()
    )
}