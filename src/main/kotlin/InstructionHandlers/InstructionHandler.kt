package InstructionHandlers

interface InstructionHandler {
    fun handleInstruction(instructionSplit: List<String>)
}