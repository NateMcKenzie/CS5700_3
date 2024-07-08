import java.io.File
import kotlin.concurrent.timer

class FileReader (
    fileName: String
): Subject{
    private val bufferedReader = File(fileName).bufferedReader()
    private var observer: Observer? = null
    var nextInstruction = ""

    init {
        timer(initialDelay = 0L, period = 1000L) {
            readInstruction()
        }
    }

    fun readInstruction(){
        nextInstruction = bufferedReader.readLine()
        observer?.update()
    }

    override fun subscribe(observer: Observer) {
        this.observer = observer
    }

    override fun unsubscribe() {
        this.observer = null
    }


}