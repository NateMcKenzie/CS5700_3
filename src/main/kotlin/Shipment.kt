class Shipment(
    status: Status,
    val id: String,
    expectedDeliveryDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf()
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
    private var observers: MutableList<Observer> = mutableListOf()

    fun addNote(note: String){
        _notes.add(note)
        notifySubscribers()
    }

    fun addUpdate(update: ShippingUpdate){
        _updateHistory.add(update)
        status = update.newStatus
        expectedDeliveryDateTimestamp = update.newDeliveryDate
        currentLocation = update.newLocation
        notifySubscribers()
    }

    override fun notifySubscribers(){
        observers.forEach {
           it.update()
        }
    }

    override fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }
}

enum class Status {
    Created, Shipped, Lost, Canceled, Delivered, Unknown
}