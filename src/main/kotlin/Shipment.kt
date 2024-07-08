class Shipment(
    status: String,
    val id: String,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String,
    notes: List<String> = listOf<String>()
): Subject{
    var status = status
        private set
    var expectedDeliveryDateTimestamp = expectedDeliveryDateTimestamp
        private set
    var currentLocation = currentLocation
        private set
    private val _notes = notes.toMutableList()
    val notes: List<String> get() = _notes.toList()
    private val _updateHistory : MutableList<ShippingUpdate> = mutableListOf()
    val updateHistory: List<ShippingUpdate> get() = _updateHistory.toList()
    private var observer: Observer? = null

    fun addNote(note: String){
        _notes.add(note)
        notifySubscriber()
    }

    fun addUpdate(update: ShippingUpdate){
        _updateHistory.add(update)
        status = update.newStatus
        expectedDeliveryDateTimestamp = update.newDeliveryDate
        currentLocation = update.newLocation
        notifySubscriber()
    }

    private fun notifySubscriber(){
        observer?.update()
    }

    override fun subscribe(observer: Observer) {
        this.observer = observer
    }

    override fun unsubscribe() {
        this.observer = null
    }
}