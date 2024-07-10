package InstructionHandlers

import TrackingSimulator

class AddNote : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>) {
        val shipment = TrackingSimulator.findShipment(instructionSplit[0])
        shipment?.addNote(instructionSplit[2])
    }

}
