package instructionHandlerTests

import Shipment
import instructionHandlers.Cancel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CancelTest {
    @Test
    fun cancelTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Cancel().handleInstruction("s10000,1652712855468".split(','),shipment)
        assertEquals(Status.Canceled, shipment.status)
    }

    @Test
    fun badTimestampTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Cancel().handleInstruction("s10000,1a652712855468".split(','),shipment)
        }
    }
}