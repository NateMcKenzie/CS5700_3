class Shipment(
    status: String,
    val id: String,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String,
    val notes: MutableList<String> = mutableListOf<String>()
): Subject{
    var status = status
        private set
    var expectedDeliveryDateTimestamp = expectedDeliveryDateTimestamp
        private set
    var currentLocation = currentLocation
        private set
    val updateHistory : MutableList<ShippingUpdate> = mutableListOf<ShippingUpdate>()
    private var observer: Observer? = null

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

    override fun subscribe(observer: Observer) {
        this.observer = observer
    }

    override fun unsubscribe() {
        this.observer = null
    }
}