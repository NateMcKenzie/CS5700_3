import InstructionHandlers.*

object TrackingSimulator:Observer {
    private val shipments = mutableSetOf<Shipment>()
    private val instructionMap = mapOf(
        "created" to CreateShipment(),
        "shipped" to Ship(),
        "location" to UpdateLocation(),
        "delayed" to Delay(),
        "noteadded" to AddNote(),
        "lost" to Lose(),
        "canceled" to Cancel(),
        "delivered" to Deliver()
    )
    private lateinit var instructionStream : FileReader

    fun findShipment(id: String) = shipments.find {
            it.id == id
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun runSimulation(inputFile : String) {
        instructionStream = FileReader(inputFile)
        instructionStream.subscribe(this)
        instructionStream.start()
    }
    override fun update() {
        val splits = instructionStream.nextInstruction.split(',')
        instructionMap[splits[0]]?.handleInstruction(splits.drop(1))
    }

    fun clearShipments() {
        shipments.clear()
        print("\n\nDELTE THIS WHEN YOU FIND A BETTER WAY TO TEST\n\n")
    }
}
