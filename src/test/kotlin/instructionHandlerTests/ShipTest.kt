package instructionHandlerTests

import Shipment
import Status
import instructionHandlers.Ship
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ShipTest {
    @Test
    fun shipTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Ship().handleInstruction("s10000,1652712855468,1652712999999".split(','), shipment)
        assertEquals(Status.Shipped, shipment.status)
        assertEquals(1652712999999, shipment.expectedDeliveryDateTimestamp)
    }

    @Test
    fun badFirstTimestampTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Ship().handleInstruction("s10000,I'm bad1652712855468,1652712999999".split(','), shipment)
        }
    }

    @Test
    fun badSecondTimestampTest() {
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Ship().handleInstruction("s10000,1652712855468,1now652I'm712bad999999".split(','), shipment)
        }
    }

    @Test
    fun nullShipmentTest() {
        assertDoesNotThrow {
            Ship().handleInstruction("s10000,1652712855468,1652712855468".split(','), null)
        }
    }
}