import InstructionStreams.FileReader
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileNotFoundException
import kotlin.test.*

class TrackingManagerTest {
    @AfterTest
    fun clear() = TrackingManager.clearShipments()

    @Test
    fun runSimulationGoodTest() {
        assertDoesNotThrow {
            TrackingManager.runSimulation(FileReader("res/short.txt", 1))
        }
    }

    @Test
    fun runSimulationBadTest() {
        assertFailsWith<FileNotFoundException> {
            TrackingManager.runSimulation(FileReader("res/does_not_exist_at_all.txt", 1))
        }
    }

    @Test
    fun runThroughTest() = runBlocking {
        assertDoesNotThrow {
            TrackingManager.runSimulation(FileReader("res/fileReaderTest.txt", 10))
            delay(110)
            assertEquals(Status.Delivered, TrackingManager.findShipment("s1")?.status)
            assertContentEquals(
                listOf("package was damaged slightly during shipping"),
                TrackingManager.findShipment("s1")?.notes
            )
            assertEquals(Status.Shipped, TrackingManager.findShipment("s2")?.status)
            TrackingManager.findShipment("s2")?.notes?.let { assertEquals(0, it.size) }
            TrackingManager.clearShipments()
        }
    }

    @Test
    fun addFindShipmentTest() {
        val shipment1 = Shipment(Status.Shipped, "s1000", 20000L, "Salt Lake City, UT")
        val shipment2 = Shipment(Status.Shipped, "s2000", 21000L, "Salt Lake City, UT")
        val shipment3 = Shipment(Status.Shipped, "s3000", 22000L, "Salt Lake City, UT")
        TrackingManager.addShipment(shipment1)
        TrackingManager.addShipment(shipment2)
        TrackingManager.addShipment(shipment3)
        assertEquals(shipment1, TrackingManager.findShipment("s1000"))
        assertEquals(shipment2, TrackingManager.findShipment("s2000"))
        assertEquals(shipment3, TrackingManager.findShipment("s3000"))
    }

    @Test
    fun findFailTest() {
        assertNull(TrackingManager.findShipment("not_a_real_id"))
    }

    @Test
    fun commalessInstructionTest() = runBlocking {
        assertDoesNotThrow {
            TrackingManager.runSimulation(FileReader("res/commaless.txt", 1))
            delay(3)
        }
    }

    @Test
    fun nonexistantInstructionTest() = runBlocking {
        assertDoesNotThrow {
            TrackingManager.runSimulation(FileReader("res/nonexistantInstruction.txt", 1))
            delay(8)
        }
    }
    //Test for duplicates removed by using MutableSet for shipments instead
}