package InstructionHandlers

import ShippingUpdate
import TrackingSimulator

class Cancel : InstructionHandler {
    override fun handleInstruction(instruction: List<String>) {
        val shipment = TrackingSimulator.findShipment(instruction[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instruction[1].toLong(), Status.canceled))
    }
}
