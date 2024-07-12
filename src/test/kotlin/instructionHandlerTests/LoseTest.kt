package instructionHandlerTests

import Shipment
import instructionHandlers.Lose
import kotlin.test.Test
import kotlin.test.assertEquals

class LoseTest {
    @Test
    fun loseTest(){
        val shipment = Shipment(Status.Shipped, "s10000", 20000L)
        Lose().handleInstruction("s10000,1652712855468".split(','),shipment)
        assertEquals(Status.Lost, shipment.status)
    }
}