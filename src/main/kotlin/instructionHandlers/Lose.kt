package instructionHandlers

import Shipment
import ShippingUpdate
import TrackingSimulator

class Lose: InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), newStatus = Status.Lost, newLocation = "Last seen: ${shipment.currentLocation}"))
    }

}
