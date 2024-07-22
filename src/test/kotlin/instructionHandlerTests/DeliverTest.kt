package instructionHandlerTests

import instructionHandlers.Deliver
import org.junit.jupiter.api.assertDoesNotThrow
import shipments.StandardShipment
import shipments.Status
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DeliverTest {
    @Test
    fun deliverTest() {
        val shipment = StandardShipment(Status.Shipped, "s10000", 20000L)
        Deliver().handleInstruction("s10000,1652712855468".split(','), shipment)
        assertEquals(Status.Delivered, shipment.status)
    }

    @Test
    fun badTimestampTest() {
        val shipment = StandardShipment(Status.Shipped, "s10000", 20000L)
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