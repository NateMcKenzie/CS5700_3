import instructionHandlers.InstructionHandler
import shipments.Shipment
import shipments.Status
import shipments.shipmentFactory

class CreateShipment : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, _unused: Shipment?) {
        try {
            val shipment =
                shipmentFactory(instructionSplit[2], Status.Created, instructionSplit[0], instructionSplit[1].toLong())
            TrackingManager.addShipment(shipment)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(
                "Invalid timestamp: '${instructionSplit[1]}'",
                e
            )
        }
    }
}