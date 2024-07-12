package instructionHandlerTests

import Shipment
import instructionHandlers.Deliver
import kotlin.test.Test
import kotlin.test.assertEquals

class DeliverTest {
    @Test
    fun deliverTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Deliver().handleInstruction("s10000,1652712855468".split(','),shipment)
        assertEquals(Status.Delivered, shipment.status)
    }
}