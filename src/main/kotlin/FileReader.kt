import java.io.File
import java.util.*
import kotlin.concurrent.timer

class FileReader (
    fileName: String,
    private val period: Long = 1000L
): Subject{
    var nextInstruction :String = ""
        private set
    private val file = File(fileName).bufferedReader()
    private var observers: MutableList<Observer> = mutableListOf()
    private lateinit var timer : Timer

    fun start(){
        timer = timer(initialDelay = 0L, period = period) {
            readInstruction()
        }
    }

    fun readInstruction(){
        //Credit to Claude AI for this kotlin idiom
        file.readLine()?.also { line ->
            nextInstruction = line
            notifySubscribers()
        } ?: run {
            timer.cancel()
            return
        }
    }

    override fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    override fun unsubscribe(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifySubscribers() {
        observers.forEach { it.update() }
    }
}