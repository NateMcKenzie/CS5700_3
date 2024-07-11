package InstructionHandlers

import ShippingUpdate
import TrackingSimulator

class Cancel : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = TrackingSimulator.findShipment(instructionSplit[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), Status.Canceled))
    }
}
