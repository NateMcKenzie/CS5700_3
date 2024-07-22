package shipments

import Observer
import ShippingUpdate
import Subject
import java.time.Duration
import java.time.Instant

abstract class Shipment(
    status: Status,
    val id: String,
    val createdDateTimestamp: Long,
    currentLocation: String = "Warehouse",
    notes: List<String> = listOf(),
) : Subject {
    var status = status
        private set
    var expectedDeliveryDateTimestamp: Long = 0L
        private set(value) {
            field = value
            validate()
        }
    var currentLocation = currentLocation
        private set
    private val _notes = notes.toMutableList()
    val notes: List<String> get() = _notes.toList()
    private val _updateHistory: MutableList<ShippingUpdate> = mutableListOf()
    val updateHistory: List<ShippingUpdate> get() = _updateHistory.toList()
    private var observers: MutableList<Observer> = mutableListOf()
    var invalidReason = ""

    abstract fun validate()

    fun addNote(note: String) {
        _notes.add(note)
        notifySubscribers()
    }

    fun addUpdate(update: ShippingUpdate) {
        _updateHistory.add(update)
        status = update.newStatus
        expectedDeliveryDateTimestamp = update.newDeliveryDate
        currentLocation = update.newLocation
        notifySubscribers()
    }

    protected fun calculateDays(first: Long, second: Long): Long {
        val firstInstant = Instant.ofEpochMilli(first)
        val secondInstant = Instant.ofEpochMilli(second)
        return Duration.between(firstInstant, secondInstant).toDays()
    }

    protected fun markInvalid(reason: String) {
        if (status == Status.Invalid) return
        invalidReason = "Invalid: $reason"
        addUpdate(ShippingUpdate(this, Instant.now().toEpochMilli(), newStatus = Status.Invalid))
    }

    protected fun markValid() {
        invalidReason = ""
    }

    override fun notifySubscribers() {
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
    Created, Shipped, Lost, Canceled, Delivered, Unknown, Delayed, Invalid
}