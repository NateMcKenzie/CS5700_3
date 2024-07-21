package instructionHandlers

import shipments.Shipment
import ShippingUpdate
import shipments.Status

class Ship : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        try {
            shipment?.addUpdate(
                ShippingUpdate(
                    shipment,
                    instructionSplit[1].toLong(),
                    newStatus = Status.Shipped,
                    newDeliveryDate = instructionSplit[2].toLong()
                )
            )
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Invalid number values: '${instructionSplit[1]}' or '${instructionSplit[2]}'",
                e
            )
        }
    }

}
