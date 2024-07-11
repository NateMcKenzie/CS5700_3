package InstructionHandlers

import ShippingUpdate

class UpdateLocation : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = TrackingSimulator.findShipment(instructionSplit[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(),  newLocation = instructionSplit[2]))
    }

}
