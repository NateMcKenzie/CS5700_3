package instructionHandlerTests

import Shipment
import instructionHandlers.UpdateLocation
import kotlin.test.Test
import kotlin.test.assertEquals

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
}