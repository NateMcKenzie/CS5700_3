package shipments

import ObserverTestHelper
import ShippingUpdate
import kotlin.test.*

class ShipmentTest {
    @Test
    fun constructorNotelessTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertEquals(Status.Shipped, shipment.status)
        assertEquals("s1000", shipment.id)
        assertEquals(20000L, shipment.createdDateTimestamp)
        assertEquals("Logan, UT", shipment.currentLocation)
        assertTrue(shipment.notes.isEmpty())
    }

    @Test
    fun constructorNotedTest() {
        val shipment =
            StandardShipment(
                Status.Shipped,
                "s1000",
                20000L,
                "Logan, UT",
                listOf("Imported from Canada", "Passed border")
            )
        assertEquals(Status.Shipped, shipment.status)
        assertEquals("s1000", shipment.id)
        assertEquals(20000L, shipment.createdDateTimestamp)
        assertEquals("Logan, UT", shipment.currentLocation)
        assertContentEquals(listOf("Imported from Canada", "Passed border"), shipment.notes)
    }

    @Test
    fun addNoteTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
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
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertEquals(Status.Shipped, shipment.status)
        shipment.addUpdate(ShippingUpdate(shipment, 19000, newStatus = Status.Delivered))
        assertEquals(Status.Delivered, shipment.status)
    }

    @Test
    fun addUpdateLocationTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Salt Lake City, UT")
        assertEquals("Salt Lake City, UT", shipment.currentLocation)
        shipment.addUpdate(ShippingUpdate(shipment, 19000, newLocation = "Logan, UT"))
        assertEquals("Logan, UT", shipment.currentLocation)
    }

    @Test
    fun addUpdateDateTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        assertEquals(20000L, shipment.createdDateTimestamp)
        shipment.addUpdate(ShippingUpdate(shipment, 15000, newDeliveryDate = 18000L))
        assertEquals(18000L, shipment.expectedDeliveryDateTimestamp)
        shipment.addUpdate(ShippingUpdate(shipment, 17000, newDeliveryDate = 28000L))
        assertEquals(28000L, shipment.expectedDeliveryDateTimestamp)
    }

    @Test
    fun subscriberNoteTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        val observer = ObserverTestHelper()
        shipment.subscribe(observer)
        assertFalse { observer.triggered }
        shipment.addNote("New note")
        assertTrue { observer.triggered }
    }

    @Test
    fun subscriberUpdateTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        val observer = ObserverTestHelper()
        shipment.subscribe(observer)
        assertFalse { observer.triggered }
        shipment.addUpdate(ShippingUpdate(shipment, 15000, newDeliveryDate = 18000L))
        assertTrue { observer.triggered }
    }

    @Test
    fun unsubscribeTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Logan, UT")
        val observer = ObserverTestHelper()
        shipment.subscribe(observer)
        assertFalse { observer.triggered }
        shipment.unsubscribe(observer)
        shipment.addUpdate(ShippingUpdate(shipment, 15000, newDeliveryDate = 18000L))
        assertFalse { observer.triggered }
    }

    @Test
    fun updateHistoryTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 20000L, "Salt Lake City, UT")
        val update1 = ShippingUpdate(shipment, 19000, newLocation = "Logan, UT")
        val update2 = ShippingUpdate(shipment, 19000, newLocation = "Utah State University, UT")
        val update3 = ShippingUpdate(shipment, 19000, newDeliveryDate = 19000L)
        val update4 = ShippingUpdate(shipment, 19000, newStatus = Status.Delivered)
        shipment.addUpdate(update1)
        shipment.addUpdate(update2)
        shipment.addUpdate(update3)
        shipment.addUpdate(update4)
        assertContains(shipment.updateHistory, update1)
        assertContains(shipment.updateHistory, update2)
        assertContains(shipment.updateHistory, update3)
        assertContains(shipment.updateHistory, update4)
    }

    @Test
    fun markInvalidTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 200000000L, "Salt Lake City, UT")
        shipment.addUpdate(ShippingUpdate(shipment, 0, newDeliveryDate = 1, newStatus = Status.Shipped))
        assert(shipment.status == Status.Invalid)
        assertEquals("Invalid: Delivery date is earlier than shipment date.", shipment.invalidReason)
    }

    @Test
    fun markValidTest() {
        val shipment = StandardShipment(Status.Shipped, "s1000", 200000000L, "Salt Lake City, UT")
        shipment.addUpdate(ShippingUpdate(shipment, 0, newDeliveryDate = 1, newStatus = Status.Shipped))
        assert(shipment.status == Status.Invalid)
        assertEquals("Invalid: Delivery date is earlier than shipment date.", shipment.invalidReason)
        shipment.addUpdate(ShippingUpdate(shipment, 0, newDeliveryDate = 200000000L, newStatus = Status.Shipped))
        assert(shipment.status == Status.Shipped)
        assertEquals("", shipment.invalidReason)
    }

    @Test
    fun calculateDaysTest() {
        // 7/23/24 12:00.000AM
        assertEquals(1L, Shipment.Companion.calculateDays(1721714400000, 1721800800000))
        // 7/24/24 12:00.000AM
        assertEquals(0L, Shipment.Companion.calculateDays(1721714400000, 1721800799999))
    }

    @Test
    fun calculateDaysLongTest() {
        // 1 Week
        assertEquals(7L, Shipment.Companion.calculateDays(1721714400000, 1722319200000))
        // 1 Year
        assertEquals(365L, Shipment.Companion.calculateDays(1672531200000, 1704067200000))
    }

    @Test
    fun calculateDaysNegativeTest() {
        // Negative 1 day
        assertEquals(-1L, Shipment.Companion.calculateDays(1721800800000, 1721714400000))
    }
}
