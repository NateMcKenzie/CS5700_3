package instructionHandlers

import Shipment
import ShippingUpdate

class UpdateLocation : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(),  newLocation = instructionSplit[2]))
    }

}
