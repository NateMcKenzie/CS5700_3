package InstructionHandlers

import ShippingUpdate
import TrackingSimulator

class Deliver : InstructionHandler {
    override fun handleInstruction(instruction: List<String>) {
        val shipment = TrackingSimulator.findShipment(instruction[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instruction[1].toLong(), newStatus = Status.delivered, newDeliveryDate = instruction[1].toLong()))
    }

}
