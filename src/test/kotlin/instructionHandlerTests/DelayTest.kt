package instructionHandlerTests

import Shipment
import instructionHandlers.Delay
import kotlin.test.Test
import kotlin.test.assertEquals

class DelayTest {
    @Test
    fun delayTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Delay().handleInstruction("s10000,1652712855468,1652712859999".split(','),shipment)
        assertEquals(1652712859999, shipment.expectedDeliveryDateTimestamp)
    }
}