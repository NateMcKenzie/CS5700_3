class Shipment(
    status: String,
    val id: String,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String,
    val notes: MutableList<String> = mutableListOf<String>()
){
    var status = status
        private set
    var expectedDeliveryDateTimestamp = expectedDeliveryDateTimestamp
        private set
    var currentLocation = currentLocation
        private set
    val updateHistory : MutableList<ShippingUpdate> = mutableListOf<ShippingUpdate>()
    private val observer: Observer? = null

    fun addNote(note: String){
        notes.add(note)
        notifySubscriber()
    }

    fun addUpdate(update: ShippingUpdate){
        updateHistory.add(update)
        status = update.newStatus
        expectedDeliveryDateTimestamp = update.newDeliveryDate
        currentLocation = update.newLocation
        notifySubscriber()
    }

    fun notifySubscriber(){
        observer?.update()
    }
}