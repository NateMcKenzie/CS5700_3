package shipments

import ShippingUpdate
import kotlin.test.Test
import kotlin.test.assertEquals

class BulkShipmentTest {
    @Test
    fun validatePastTest() {
        val bulk = BulkShipment(Status.Created, "s0", 100L)
        bulk.addUpdate(ShippingUpdate(bulk, 110L, newDeliveryDate = 10L, newStatus = Status.Shipped))
        assertEquals(Status.Invalid, bulk.status)
        assertEquals("Invalid: Delivery date is earlier than shipment date.", bulk.invalidReason)
    }

    @Test
    fun validateShortTest() {
        val bulk = BulkShipment(Status.Created, "s0", 1721714400000)
        bulk.addUpdate(ShippingUpdate(bulk, 1721714400000, newDeliveryDate = 1721973599999, newStatus = Status.Shipped))
        assertEquals(Status.Invalid, bulk.status)
        assertEquals("Invalid: Bulk shipment scheduled to arrive in fewer than 3 days.", bulk.invalidReason)
    }

    @Test
    fun validateBarelyTest() {
        val bulk = BulkShipment(Status.Created, "s0", 1721714400000)
        bulk.addUpdate(ShippingUpdate(bulk, 1721714400000, newDeliveryDate = 1721973600000, newStatus = Status.Shipped))
        assertEquals(Status.Shipped, bulk.status)
        assertEquals("", bulk.invalidReason)
    }

    @Test
    fun revalidateTest() {
        val bulk = BulkShipment(Status.Created, "s0", 1721714400000)
        bulk.addUpdate(ShippingUpdate(bulk, 1721714400000, newDeliveryDate = 1721973599999, newStatus = Status.Shipped))
        assertEquals(Status.Invalid, bulk.status)
        assertEquals("Invalid: Bulk shipment scheduled to arrive in fewer than 3 days.", bulk.invalidReason)
        bulk.addUpdate(ShippingUpdate(bulk, 1721714400000, newDeliveryDate = 1721973600000, newStatus = Status.Shipped))
        assertEquals(Status.Shipped, bulk.status)
        assertEquals("", bulk.invalidReason)
    }
}