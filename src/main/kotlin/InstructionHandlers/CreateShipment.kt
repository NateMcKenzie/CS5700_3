import InstructionHandlers.InstructionHandler

class CreateShipment : InstructionHandler {
    override fun handleInstruction(instruction: List<String>) {
        val shipment = Shipment(Status.created,instruction[0],instruction[1].toLong())
        TrackingSimulator.addShipment(shipment)
    }

}