import InstructionHandlers.InstructionHandler

class CreateShipment : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = Shipment(Status.Created,instructionSplit[0],instructionSplit[1].toLong())
        TrackingSimulator.addShipment(shipment)
    }

}