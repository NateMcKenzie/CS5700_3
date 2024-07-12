import instructionHandlers.InstructionHandler

class CreateShipment : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, _unused: Shipment?) {
        val shipment = Shipment(Status.Created,instructionSplit[0],instructionSplit[1].toLong())
        TrackingSimulator.addShipment(shipment)
    }
}