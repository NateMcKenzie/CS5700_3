package instructionHandlerTests

import Shipment
import instructionHandlers.Delay
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DelayTest {
    @Test
    fun delayTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Delay().handleInstruction("s10000,1652712855468,1652712859999".split(','),shipment)
        assertEquals(1652712859999, shipment.expectedDeliveryDateTimestamp)
    }

    @Test
    fun badFirstTimestampTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Delay().handleInstruction("s10000,hello1652712855468,1652712859999".split(','),shipment)
        }
    }

    @Test
    fun badSecondTimestampTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Delay().handleInstruction("s10000,1652712855468,16hi52712859999".split(','),shipment)
        }
    }

    @Test
    fun nullShipmentTest() {
        assertDoesNotThrow {
            Delay().handleInstruction("s10000,1652712855468,1652712855468".split(','),null)
        }
    }
}