interface Subject {
    fun subscribe(observer: Observer)
    fun unsubscribe()
}