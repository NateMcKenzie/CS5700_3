package instructionHandlerTests

import CreateShipment
import Shipment
import TrackingSimulator
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CreateShipmentTest {
    @Test
    fun createShipmentTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        CreateShipment().handleInstruction("s10000,1652712855468".split(','),shipment)
        assertNotNull(TrackingSimulator.findShipment(shipment.id))
    }

    @Test
    fun badTimestampTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            CreateShipment().handleInstruction("s10000, 1652712855468".split(','), shipment)
        }
    }
}