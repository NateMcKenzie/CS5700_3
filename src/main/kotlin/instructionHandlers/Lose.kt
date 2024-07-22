package instructionHandlers

import ShippingUpdate
import shipments.Shipment
import shipments.Status

class Lose : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?) {
        try {
            shipment?.addUpdate(
                ShippingUpdate(
                    shipment,
                    instructionSplit[1].toLong(),
                    newStatus = Status.Lost,
                    newLocation = "Last seen: ${shipment.currentLocation}"
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
