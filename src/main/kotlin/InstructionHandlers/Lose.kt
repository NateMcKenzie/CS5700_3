package InstructionHandlers

import ShippingUpdate
import TrackingSimulator

class Lose: InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = TrackingSimulator.findShipment(instructionSplit[0])
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), newStatus = Status.Lost, newLocation = "Last seen: ${shipment.currentLocation}"))
    }

}
