import instructionHandlers.InstructionHandler

class CreateShipment : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        TrackingSimulator.addShipment(shipment ?: return)
    }

}