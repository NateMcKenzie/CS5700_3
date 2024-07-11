import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileNotFoundException
import javax.sound.midi.Track
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class TrackingSimulatorTest {

    @Test
    fun runSimulationGoodTest(){
        assertDoesNotThrow {
            TrackingSimulator.runSimulation("res/short.txt")
        }
    }

    @Test
    fun runSimulationBadTest(){
        assertFailsWith<FileNotFoundException> {
            TrackingSimulator.runSimulation("res/does_not_exist_at_all.txt")
        }
    }

    @Test
    fun addFindShipmentTest(){
        val shipment1 = Shipment(Status.Shipped, "s1000", 20000L, "Salt Lake City, UT")
        val shipment2 = Shipment(Status.Shipped, "s2000", 21000L, "Salt Lake City, UT")
        val shipment3 = Shipment(Status.Shipped, "s3000", 22000L, "Salt Lake City, UT")
        TrackingSimulator.addShipment(shipment1)
        TrackingSimulator.addShipment(shipment2)
        TrackingSimulator.addShipment(shipment3)
        assertEquals(shipment1, TrackingSimulator.findShipment("s1000"))
        assertEquals(shipment2, TrackingSimulator.findShipment("s2000"))
        assertEquals(shipment3, TrackingSimulator.findShipment("s3000"))
    }

    @Test
    fun findFailTest(){
        assertNull(TrackingSimulator.findShipment("not_a_real_id"))
    }

    //Test for duplicates removed by using MutableSet for shipments instead
}