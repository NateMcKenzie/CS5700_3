package instructionHandlers

import ShippingUpdate
import shipments.Shipment
import shipments.Status

class Deliver : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        try {
            shipment?.addUpdate(
                ShippingUpdate(
                    shipment,
                    instructionSplit[1].toLong(),
                    newStatus = Status.Delivered,
                    newDeliveryDate = instructionSplit[1].toLong()
                )
            )
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Invalid timestamp: '${instructionSplit[1]}'",
                e
            )
        }
    }

}
