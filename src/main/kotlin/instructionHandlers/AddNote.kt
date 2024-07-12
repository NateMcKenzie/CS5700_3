package instructionHandlers

import Shipment
import TrackingSimulator

class AddNote : InstructionHandler {
    override fun handleInstruction(instructionSplit: List<String>, shipment: Shipment) {
        shipment.addNote(instructionSplit[2])
    }

}
