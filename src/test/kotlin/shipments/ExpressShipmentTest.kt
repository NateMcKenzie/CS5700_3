package shipments

import ShippingUpdate
import kotlin.test.Test
import kotlin.test.assertEquals

class ExpressShipmentTest {
    @Test
    fun validatePastTest() {
        val express = ExpressShipment(Status.Created, "s0", 100L)
        express.addUpdate(ShippingUpdate(express, 110L, newDeliveryDate = 10L, newStatus = Status.Shipped))
        assertEquals(Status.Invalid, express.status)
        assertEquals("Invalid: Delivery date is earlier than shipment date.", express.invalidReason)
    }

    @Test
    fun validateBarelyTest() {
        val express = ExpressShipment(Status.Created, "s0", 1721714400000)
        express.addUpdate(
            ShippingUpdate(
                express,
                1721714400000,
                newDeliveryDate = 1721973599999,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Shipped, express.status)
        assertEquals("", express.invalidReason)
    }

    @Test
    fun validateLongTest() {
        val express = ExpressShipment(Status.Created, "s0", 1721714400000)
        express.addUpdate(
            ShippingUpdate(
                express,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Invalid, express.status)
        assertEquals("Invalid: Express shipment scheduled to arrive in more than 3 days.", express.invalidReason)
    }

    @Test
    fun validateDelayedTest() {
        val express = ExpressShipment(Status.Created, "s0", 1721714400000)
        express.addUpdate(
            ShippingUpdate(
                express,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Delayed
            )
        )
        assertEquals(Status.Delayed, express.status)
        assertEquals("", express.invalidReason)
        express.addUpdate(
            ShippingUpdate(
                express,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Delayed
            )
        )
        assertEquals(Status.Delayed, express.status)
        assertEquals("", express.invalidReason)
    }

    @Test
    fun revalidateTest() {
        val express = ExpressShipment(Status.Created, "s0", 1721714400000)
        express.addUpdate(
            ShippingUpdate(
                express,
                1721714400000,
                newDeliveryDate = 1722060000000,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Invalid, express.status)
        assertEquals("Invalid: Express shipment scheduled to arrive in more than 3 days.", express.invalidReason)
        express.addUpdate(
            ShippingUpdate(
                express,
                1721714400000,
                newDeliveryDate = 1721973599999,
                newStatus = Status.Shipped
            )
        )
        assertEquals(Status.Shipped, express.status)
        assertEquals("", express.invalidReason)
    }
}