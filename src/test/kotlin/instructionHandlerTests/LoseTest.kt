package instructionHandlerTests

import Shipment
import instructionHandlers.Lose
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoseTest {
    @Test
    fun loseTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Lose().handleInstruction("s10000,1652712855468".split(','),shipment)
        assertEquals(Status.Lost, shipment.status)
    }

    @Test
    fun badTimestampTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        assertFailsWith<IllegalArgumentException> {
            Lose().handleInstruction("s10000,16527128if you see this you're paying attention55468".split(','), shipment)
        }
    }
}