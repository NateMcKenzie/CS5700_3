import shipments.Shipment
import shipments.Status
import kotlin.test.*

class ShipmentTest {
    @Test
    fun constructorNotelessTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertEquals(Status.Shipped, shipment.status)
        assertEquals("s1000", shipment.id)
        assertEquals(20000L, shipment.expectedDeliveryDateTimestamp)
        assertEquals("Logan, UT", shipment.currentLocation)
        assertTrue(shipment.notes.isEmpty())
    }

    @Test
    fun constructorNotedTest() {
        val shipment =
            Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT", listOf("Imported from Canada", "Passed border"))
        assertEquals(Status.Shipped, shipment.status)
        assertEquals("s1000", shipment.id)
        assertEquals(20000L, shipment.expectedDeliveryDateTimestamp)
        assertEquals("Logan, UT", shipment.currentLocation)
        assertContentEquals(listOf("Imported from Canada", "Passed border"), shipment.notes)
    }

    @Test
    fun addNoteTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertTrue(shipment.notes.isEmpty())
        shipment.addNote("Made it to Utah")
        assertContentEquals(listOf("Made it to Utah"), shipment.notes)
        shipment.addNote("Made it to Cache")
        assertContentEquals(listOf("Made it to Utah", "Made it to Cache"), shipment.notes)
        shipment.addNote("Stuck on Main Street")
        assertContentEquals(listOf("Made it to Utah", "Made it to Cache", "Stuck on Main Street"), shipment.notes)
    }

    @Test
    fun addUpdateStatusTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertEquals(Status.Shipped, shipment.status)
        shipment.addUpdate(ShippingUpdate(shipment, 19000, newStatus = Status.Delivered))
        assertEquals(Status.Delivered, shipment.status)
    }

    @Test
    fun addUpdateLocationTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Salt Lake City, UT")
        assertEquals("Salt Lake City, UT", shipment.currentLocation)
        shipment.addUpdate(ShippingUpdate(shipment, 19000, newLocation = "Logan, UT"))
        assertEquals("Logan, UT", shipment.currentLocation)
    }

    @Test
    fun addUpdateDateTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertEquals(20000L, shipment.expectedDeliveryDateTimestamp)
        shipment.addUpdate(ShippingUpdate(shipment, 15000, newDeliveryDate = 18000L))
        assertEquals(18000L, shipment.expectedDeliveryDateTimestamp)
        shipment.addUpdate(ShippingUpdate(shipment, 17000, newDeliveryDate = 28000L))
        assertEquals(28000L, shipment.expectedDeliveryDateTimestamp)
    }

    @Test
    fun subscriberNoteTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        val observer = ObserverTestHelper()
        shipment.subscribe(observer)
        assertFalse { observer.triggered }
        shipment.addNote("New note")
        assertTrue { observer.triggered }
    }

    @Test
    fun subscriberUpdateTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        val observer = ObserverTestHelper()
        shipment.subscribe(observer)
        assertFalse { observer.triggered }
        shipment.addUpdate(ShippingUpdate(shipment, 15000, newDeliveryDate = 18000L))
        assertTrue { observer.triggered }
    }

    @Test
    fun unsubscribeTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        val observer = ObserverTestHelper()
        shipment.subscribe(observer)
        assertFalse { observer.triggered }
        shipment.unsubscribe(observer)
        shipment.addUpdate(ShippingUpdate(shipment, 15000, newDeliveryDate = 18000L))
        assertFalse { observer.triggered }
    }

    @Test
    fun updateHistoryTest() {
        val shipment = Shipment(Status.Shipped, "s1000", 20000L, "Salt Lake City, UT")
        val update1 = ShippingUpdate(shipment, 19000, newLocation = "Logan, UT")
        val update2 = ShippingUpdate(shipment, 19000, newLocation = "Utah State University, UT")
        val update3 = ShippingUpdate(shipment, 19000, newDeliveryDate = 19000L)
        val update4 = ShippingUpdate(shipment, 19000, newStatus = Status.Delivered)
        shipment.addUpdate(update1)
        shipment.addUpdate(update2)
        shipment.addUpdate(update3)
        shipment.addUpdate(update4)
        assertContentEquals(listOf(update1, update2, update3, update4), shipment.updateHistory)
    }
}
