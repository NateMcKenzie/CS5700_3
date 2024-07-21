package instructionHandlers

import shipments.Shipment
import ShippingUpdate
import shipments.Status

class Cancel : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        try {
            shipment?.addUpdate(ShippingUpdate(shipment, instructionSplit[1].toLong(), Status.Canceled))
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Invalid timestamp: '${instructionSplit[1]}'",
                e
            )
        }
    }
}
