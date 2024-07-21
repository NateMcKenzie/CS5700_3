package instructionHandlerTests

import shipments.Shipment
import shipments.Status
import instructionHandlers.Deliver
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DeliverTest {
    @Test
    fun deliverTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Deliver().handleInstruction("s10000,1652712855468".split(','), shipment)
        assertEquals(Status.Delivered, shipment.status)
    }

    @Test
    fun badTimestampTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Deliver().handleInstruction("s10000,1652712855468:)".split(','), shipment)
        }
    }

    @Test
    fun nullShipmentTest() {
        assertDoesNotThrow {
            Deliver().handleInstruction("s10000,1652712855468".split(','), null)
        }
    }
}