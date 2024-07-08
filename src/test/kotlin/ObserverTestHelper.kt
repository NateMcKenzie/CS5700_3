class ObserverTestHelper : Observer{
    var triggered = false
    override fun update() {
        triggered = true
    }
}