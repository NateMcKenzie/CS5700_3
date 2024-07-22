package shipments

import ShippingUpdate
import kotlin.test.Test
import kotlin.test.assertEquals

class OvernightShipmentTest {
    @Test
    fun validatePastTest() {
        val overnight = OvernightShipment(Status.Created, "s0", 100L)
        overnight.addUpdate(ShippingUpdate(overnight, 110L, newDeliveryDate = 10L, newStatus = Status.Shipped))
        assertEquals(Status.Invalid, overnight.status)
        assertEquals("Invalid: Delivery date is earlier than shipment date.", overnight.invalidReason)
    }

    @Test
    fun validateBarelyTest() {
        val overnight = OvernightShipment(Status.Created, "s0", 1721714400000)
        overnight.addUpdate(
            ShippingUpdate(
                overnight,
                1721714400000,
                newDeliveryDate = 1721887199999,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Shipped, overnight.status)
        assertEquals("", overnight.invalidReason)
    }

    @Test
    fun validateLongTest() {
        val overnight = OvernightShipment(Status.Created, "s0", 1721714400000)
        overnight.addUpdate(
            ShippingUpdate(
                overnight,
                1721714400000,
                newDeliveryDate = 1721887200000,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Invalid, overnight.status)
        assertEquals("Invalid: Overnight shipment scheduled to arrive later than next-day.", overnight.invalidReason)
    }

    @Test
    fun validateDelayedTest() {
        val overnight = OvernightShipment(Status.Created, "s0", 1721714400000)
        overnight.addUpdate(
            ShippingUpdate(
                overnight,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Delayed
            )
        )
        assertEquals(Status.Delayed, overnight.status)
        assertEquals("", overnight.invalidReason)
        overnight.addUpdate(
            ShippingUpdate(
                overnight,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Delayed
            )
        )
        assertEquals(Status.Delayed, overnight.status)
        assertEquals("", overnight.invalidReason)
    }

    @Test
    fun revalidateTest() {
        val overnight = OvernightShipment(Status.Created, "s0", 1721714400000)
        overnight.addUpdate(
            ShippingUpdate(
                overnight,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Invalid, overnight.status)
        assertEquals("Invalid: Overnight shipment scheduled to arrive later than next-day.", overnight.invalidReason)
        overnight.addUpdate(
            ShippingUpdate(
                overnight,
                1721714400000,
                newDeliveryDate = 1721887199999,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Shipped, overnight.status)
        assertEquals("", overnight.invalidReason)
    }
}