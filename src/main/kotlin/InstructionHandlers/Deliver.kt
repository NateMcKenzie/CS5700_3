package InstructionHandlers

import ShippingUpdate
import TrackingSimulator

class Deliver : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = TrackingSimulator.findShipment(instructionSplit[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), newStatus = Status.Delivered, newDeliveryDate = instructionSplit[1].toLong()))
    }

}
