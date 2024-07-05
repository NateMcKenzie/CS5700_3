import InstructionHandlers.*

class TrackingSimulator {
    private val shipments = mutableListOf<Shipment>();
    private val instructionMap = mapOf<String, InstructionHandler>(
        "created" to CreateShipment(),
        "shipped" to Ship(),
        "location" to UpdateLocation(),
        "delayed" to Delay(),
        "noteadded" to AddNote(),
        "lost" to Lose(),
        "canceled" to Cancel(),
        "delivered" to Deliver()
    )
    private val instructionStream = FileReader("input.txt")

    fun findShipment(id: String) : Shipment?{
        TODO("Not yet implemented")
    }

    fun addShipment(shipment: Shipment){
        TODO("Not yet implemented")
    }

    fun runSimulation(){
        TODO("Not yet implemented")
    }
}