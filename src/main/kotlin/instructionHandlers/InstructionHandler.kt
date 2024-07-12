package instructionHandlers

import Shipment

interface InstructionHandler {
    fun handleInstruction(instructionSplit: List<String>, shipment: Shipment)
}