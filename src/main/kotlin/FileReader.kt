import java.io.File
import kotlin.concurrent.timer

class FileReader (
    fileName: String
): Subject{
    private val file = File(fileName).bufferedReader()
    private var observers: MutableList<Observer> = mutableListOf()
    var nextInstruction = ""

    fun start(){
        timer(initialDelay = 0L, period = 1000L) {
            readInstruction()
        }
    }

    fun readInstruction(){
        nextInstruction = file.readLine()
        notifySubscribers()
    }

    override fun subscribe(observer: Observer) {
        TODO("Not yet implemented")
    }

    override fun unsubscribe(observer: Observer) {
        TODO("Not yet implemented")
    }

    override fun notifySubscribers() {
        TODO("Not yet implemented")
    }


}