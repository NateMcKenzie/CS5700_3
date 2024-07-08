import kotlin.test.*

class ShipmentTest {
    @Test
    fun constructorNotelessTest() {
        val shipment = Shipment("shipped", "s1000", 20000L, "Logan, UT")
        assertEquals("shipped", shipment.status)
        assertEquals("s1000", shipment.id)
        assertEquals(20000L, shipment.expectedDeliveryDateTimestamp)
        assertEquals("Logan, UT", shipment.currentLocation)
        assertTrue(shipment.notes.isEmpty())
    }

    @Test
    fun constructorNotedTest() {
        val shipment = Shipment("shipped", "s1000", 20000L, "Logan, UT", listOf("Imported from Canada", "Passed border"))
        assertEquals("shipped", shipment.status)
        assertEquals("s1000", shipment.id)
        assertEquals(20000L, shipment.expectedDeliveryDateTimestamp)
        assertEquals("Logan, UT", shipment.currentLocation)
        assertContentEquals(listOf("Imported from Canada", "Passed border"), shipment.notes)
    }

    @Test
    fun constructorInvalidStatusTest() {
        assertFailsWith<IllegalArgumentException> {
            val shipment =
                Shipment("Chilling", "s1000", 20000L, "Logan, UT", listOf("Imported from Canada", "Passed border"))
        }
    }

    @Test
    fun addNoteTest() {
        val shipment = Shipment("shipped", "s1000", 20000L, "Logan, UT")
        assertTrue(shipment.notes.isEmpty())
        shipment.addNote("Made it to Utah")
        assertContentEquals(listOf("Made it to Utah"), shipment.notes)
        shipment.addNote("Made it to Cache")
        assertContentEquals(listOf("Made it to Utah", "Made it to Cache"), shipment.notes)
        shipment.addNote("Stuck on Main Street")
        assertContentEquals(listOf("Made it to Utah", "Made it to Cache", "Stuck on Main Street"), shipment.notes)
    }

    @Test
    fun addUpdateTest() {
        val shipment = Shipment("shipped", "s1000", 20000L, "Logan, UT")
        shipment.addUpdate(ShippingUpdate())
    }
}
