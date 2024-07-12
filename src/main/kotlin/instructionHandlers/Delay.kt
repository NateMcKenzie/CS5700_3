package instructionHandlers

import Shipment
import ShippingUpdate
import TrackingSimulator

class Delay : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment) {
        shipment.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), newDeliveryDate = instructionSplit[2].toLong()))
    }

}
