package instructionHandlers

import Shipment
import ShippingUpdate
import TrackingSimulator

class Cancel : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment) {
        shipment.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), Status.Canceled))
    }
}
