package instructionHandlerTests

import shipments.Shipment
import shipments.Status
import instructionHandlers.UpdateLocation
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class UpdateLocationTest {
    @Test
    fun shipCommaTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        UpdateLocation().handleInstruction("s10000,1652712855468,Logan, UT".split(','), shipment)
        assertEquals("Logan, UT", shipment.currentLocation)
    }

    @Test
    fun shipNoCommaTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        UpdateLocation().handleInstruction("s10000,1652712855468,Logan UT".split(','), shipment)
        assertEquals("Logan UT", shipment.currentLocation)
    }

    @Test
    fun badTimestampTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            UpdateLocation().handleInstruction("s10000,1short652712855468,Logan UT".split(','), shipment)
        }
    }

    @Test
    fun nullShipmentTest() {
        assertDoesNotThrow {
            UpdateLocation().handleInstruction("s10000,1652712855468,China".split(','), null)
        }
    }
}