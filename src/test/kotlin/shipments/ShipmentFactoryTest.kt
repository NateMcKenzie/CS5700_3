package shipments

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class ShipmentFactoryTest {
    @Test
    fun standardTest() {
        assertIs<StandardShipment>(shipmentFactory("standard", Status.Created, "s1", 0L))
    }

    @Test
    fun bulkTest() {
        assertIs<BulkShipment>(shipmentFactory("bulk", Status.Created, "s1", 0L))
    }

    @Test
    fun expressTest() {
        assertIs<ExpressShipment>(shipmentFactory("express", Status.Created, "s1", 0L))
    }

    @Test
    fun overnightTest() {
        assertIs<OvernightShipment>(shipmentFactory("overnight", Status.Created, "s1", 0L))
    }

    @Test
    fun notImplementedTest() {
        assertFailsWith<NotImplementedError> {
            shipmentFactory("overnightexpress2.0", Status.Created, "s1", 0L)
        }
    }
}