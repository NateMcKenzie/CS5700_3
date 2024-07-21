package instructionHandlerTests

import CreateShipment
import shipments.Shipment
import shipments.Status
import TrackingManager
import org.junit.jupiter.api.assertDoesNotThrow
import shipments.StandardShipment
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CreateShipmentTest {
    @Test
    fun createShipmentTest() {
        val shipment = StandardShipment(Status.Shipped, "s10000", 20000L)
        CreateShipment().handleInstruction("s10000,1652712855468".split(','), shipment)
        assertNotNull(TrackingManager.findShipment(shipment.id))
    }

    @Test
    fun badTimestampTest() {
        val shipment = StandardShipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            CreateShipment().handleInstruction("s10000, 1652712855468".split(','), shipment)
        }
    }

    @Test
    fun nullShipmentTest() {
        assertDoesNotThrow {
            CreateShipment().handleInstruction("s10000,1652712855468".split(','), null)
        }
    }
}