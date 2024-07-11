import InstructionHandlers.*

class TrackingSimulator {
    companion object :Observer {
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
        private val instructionStream = FileReader("res/input.txt")

        fun findShipment(id: String) = shipments.find {
                it.id == id
        }

        fun addShipment(shipment: Shipment) {
            shipments.add(shipment)
        }

        fun runSimulation() {
            instructionStream.start()
            instructionStream.subscribe(this)
        }
        override fun update() {
            val splits = instructionStream.nextInstruction.split(',')
            instructionMap[splits[0]]?.handleInstruction(splits.drop(1))
        }
    }

}