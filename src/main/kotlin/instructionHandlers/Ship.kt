package instructionHandlers

import Shipment
import ShippingUpdate

class Ship : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment) {
        shipment.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), newStatus = Status.Shipped, newDeliveryDate = instructionSplit[2].toLong()))
    }

}
