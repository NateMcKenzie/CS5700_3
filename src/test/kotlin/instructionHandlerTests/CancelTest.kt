package instructionHandlerTests

import Shipment
import instructionHandlers.Cancel
import kotlin.test.Test
import kotlin.test.assertEquals

class CancelTest {
    @Test
    fun cancelTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Cancel().handleInstruction("s10000,1652712855468".split(','),shipment)
        assertEquals(Status.Canceled, shipment.status)
    }
}