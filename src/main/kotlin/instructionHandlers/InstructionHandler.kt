package instructionHandlers

import shipments.Shipment

interface InstructionHandler {
    fun handleInstruction(instructionSplit: List<String>, shipment: Shipment?)
}