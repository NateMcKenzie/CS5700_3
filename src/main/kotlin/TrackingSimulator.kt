import instructionHandlers.*

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
    var instructionCount = 0
        private set

    fun findShipment(id: String) = shipments.find {
            it.id == id
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun runSimulation(inputFile : String, simulationSpeed : Long = 1000L) {
        instructionCount = 0
        instructionStream = FileReader(inputFile, simulationSpeed)
        instructionStream.subscribe(this)
        instructionStream.start()
    }
    override fun update() {
        instructionCount++
        val splits = instructionStream.nextInstruction.split(',')
        if (splits.size < 2) throw IllegalArgumentException("Invalid line in input file: at line $instructionCount")
        val handler = instructionMap[splits[0]] ?: throw NotImplementedError("${splits[0]} not a known instruction: at line $instructionCount")
        val shipment = findShipment(splits[1])
        handler.handleInstruction(splits.drop(1), shipment)
    }

    fun clearShipments() {
        shipments.clear()
        print("\n\nDELTE TrackingSimulator.clearShipments WHEN YOU FIND A BETTER WAY TO TEST\n\n")
    }
}
