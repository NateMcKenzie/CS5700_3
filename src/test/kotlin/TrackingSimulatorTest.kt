import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileNotFoundException
import javax.sound.midi.Track
import kotlin.test.*

class TrackingSimulatorTest {

    @Test
    fun runSimulationGoodTest(){
        assertDoesNotThrow {
            TrackingSimulator.runSimulation("res/short.txt")
        }
        TrackingSimulator.clearShipments()
    }

    @Test
    fun runSimulationBadTest(){
        assertFailsWith<FileNotFoundException> {
            TrackingSimulator.runSimulation("res/does_not_exist_at_all.txt")
        }
        TrackingSimulator.clearShipments()
    }

    @Test
    fun runThroughTest() = runBlocking{
        assertDoesNotThrow {
            TrackingSimulator.runSimulation("res/fileReaderTest.txt",10)
            delay(110)
            assertEquals(Status.Delivered, TrackingSimulator.findShipment("s1")?.status)
            assertContentEquals(listOf("package was damaged slightly during shipping"), TrackingSimulator.findShipment("s1")?.notes)
            assertEquals(Status.Shipped, TrackingSimulator.findShipment("s2")?.status)
            TrackingSimulator.findShipment("s2")?.notes?.let { assertEquals(0, it.size) }
            TrackingSimulator.clearShipments()
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
        TrackingSimulator.clearShipments()
    }

    @Test
    fun findFailTest(){
        assertNull(TrackingSimulator.findShipment("not_a_real_id"))
        TrackingSimulator.clearShipments()
    }

    //Test for duplicates removed by using MutableSet for shipments instead
}