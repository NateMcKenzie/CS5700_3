package InstructionHandlers

import ShippingUpdate
import TrackingSimulator

class Delay : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = TrackingSimulator.findShipment(instructionSplit[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), newDeliveryDate = instructionSplit[2].toLong()))
    }

}
