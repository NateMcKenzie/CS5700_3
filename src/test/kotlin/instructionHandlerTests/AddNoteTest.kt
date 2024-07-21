package instructionHandlerTests

import shipments.Shipment
import shipments.Status
import instructionHandlers.AddNote
import org.junit.jupiter.api.assertDoesNotThrow
import shipments.StandardShipment
import kotlin.test.Test
import kotlin.test.assertContentEquals

class AddNoteTest {

    @Test
    fun addNoteNoCommaTest() {
        val shipment = StandardShipment(Status.Shipped, "s10000", 20000L)
        AddNote().handleInstruction(
            "s10000,1652712855468,package was damaged slightly during shipping".split(','),
            shipment
        )
        AddNote().handleInstruction(
            "s10000,1652712855468,package was repaired slightly during shipping".split(','),
            shipment
        )
        AddNote().handleInstruction("s10000,1652712855468,package was not damaged during shipping".split(','), shipment)
        assertContentEquals(
            listOf(
                "package was damaged slightly during shipping",
                "package was repaired slightly during shipping",
                "package was not damaged during shipping"
            ), shipment.notes
        )
    }

    @Test
    fun addNoteCommaTest() {
        val shipment = StandardShipment(Status.Shipped, "s10000", 20000L)
        AddNote().handleInstruction(
            "s10000,1652712855468,package ,was damaged slight,,ly, during shipping".split(','),
            shipment
        )
        AddNote().handleInstruction(
            "s10000,1652712855468,package wa,,,s re,,paired slightly during shipping".split(','),
            shipment
        )
        AddNote().handleInstruction(
            "s10000,1652712855468,package was not daa,,a,,,maged during shipping".split(','),
            shipment
        )
        assertContentEquals(
            listOf(
                "package ,was damaged slight,,ly, during shipping",
                "package wa,,,s re,,paired slightly during shipping",
                "package was not daa,,a,,,maged during shipping"
            ), shipment.notes
        )
    }

    @Test
    fun nullShipmentTest() {
        assertDoesNotThrow {
            AddNote().handleInstruction(
                "s10000,1652712855468,package was damaged slightly during shipping".split(','),
                null
            )
        }
    }
}