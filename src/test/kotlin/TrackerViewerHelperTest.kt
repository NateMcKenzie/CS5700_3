import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.*

class TrackerViewerHelperTest {
    @AfterTest
    fun clear() = TrackingSimulator.clearShipments()

    @Test
    fun defaultsTest(){
        val viewer = TrackerViewerHelper()
        assertEquals("", viewer.shipmentId)
        assertEquals(Status.Unknown, viewer.shipmentStatus)
        assertContentEquals(mutableStateListOf(), viewer.shipmentUpdateHistory)
        assertEquals(0, viewer.expectedShipmentDeliveryDate)
        assertEquals("", viewer.shipmentCurrentLocation)
        assertContentEquals(mutableStateListOf(), viewer.shipmentNotes)
    }

    @Test
    fun trackingTest() = runBlocking{
        TrackingSimulator.runSimulation("res/fileReaderTest.txt", simulationSpeed = 1L)
        val viewer = TrackerViewerHelper()
        viewer.trackShipment("s1")
        assertEquals("s1", viewer.shipmentId)
        delay(15)
        assertEquals(Status.Delivered, viewer.shipmentStatus)
        assertContentEquals(TrackingSimulator.findShipment("s1")?.updateHistory?.toSet(), viewer.shipmentUpdateHistory)
        assertEquals(1652712875870, viewer.expectedShipmentDeliveryDate)
        assertEquals("Last seen: Logan, UT", viewer.shipmentCurrentLocation)
        assertContentEquals(TrackingSimulator.findShipment("s1")?.notes, viewer.shipmentNotes)
    }

    @Test
    fun initTest() {
        TrackingSimulator.runSimulation("res/fileReaderTest.txt", simulationSpeed = 10000L)
        val viewer = TrackerViewerHelper()
        runBlocking { viewer.trackShipment("s1") }
        assertEquals("s1", viewer.shipmentId)
        assertEquals(Status.Created, viewer.shipmentStatus)
        assertContentEquals(TrackingSimulator.findShipment("s1")?.updateHistory, viewer.shipmentUpdateHistory)
        assertEquals(1652712855468, viewer.expectedShipmentDeliveryDate)
        assertEquals("Warehouse", viewer.shipmentCurrentLocation)
        assertContentEquals(TrackingSimulator.findShipment("s1")?.notes, viewer.shipmentNotes)
    }

    @Test
    fun delayedInitTest() = runBlocking{
        TrackingSimulator.runSimulation("res/fileReaderTest.txt", simulationSpeed = 1L)
        val viewer = TrackerViewerHelper()
        delay(15)
        viewer.trackShipment("s1")
        assertEquals("s1", viewer.shipmentId)
        assertEquals(Status.Delivered, viewer.shipmentStatus)
        assertContentEquals(TrackingSimulator.findShipment("s1")?.updateHistory, viewer.shipmentUpdateHistory)
        assertEquals(1652712875870, viewer.expectedShipmentDeliveryDate)
        assertEquals("Last seen: Logan, UT", viewer.shipmentCurrentLocation)
        assertContentEquals(TrackingSimulator.findShipment("s1")?.notes, viewer.shipmentNotes)
    }

    @Test
    fun stopTrackingTest() {
        TrackingSimulator.runSimulation("res/fileReaderTest.txt", simulationSpeed = 1L)
        val viewer = TrackerViewerHelper()
        runBlocking { viewer.trackShipment("s1") }
        assertEquals("s1", viewer.shipmentId)
        viewer.stopTracking()
        assertEquals("No Longer Tracking", viewer.shipmentId)
    }

    @Test
    fun hallucinatedIdTest() {
        val viewer = TrackerViewerHelper()
        assertFailsWith<IllegalStateException> {
            viewer.trackShipment("NeverHasBeenARealShipmentId")
        }
    }

    @Test
    fun stopTrackingNothingTest() {
        val viewer = TrackerViewerHelper()
        assertDoesNotThrow {
            viewer.stopTracking()
        }
    }

    @Test
    fun updateNothingTest() {
        val viewer = TrackerViewerHelper()
        assertDoesNotThrow {
            viewer.update()
        }
    }
}