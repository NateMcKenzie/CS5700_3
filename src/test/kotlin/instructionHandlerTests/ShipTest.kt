package instructionHandlerTests

import Shipment
import instructionHandlers.Ship
import kotlin.test.Test
import kotlin.test.assertEquals

class ShipTest {
    @Test
    fun shipTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Ship().handleInstruction("s10000,1652712855468,1652712999999".split(','),shipment)
        assertEquals(Status.Shipped, shipment.status)
        assertEquals(1652712999999, shipment.expectedDeliveryDateTimestamp)
    }
}