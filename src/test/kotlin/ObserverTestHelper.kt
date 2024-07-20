class ObserverTestHelper : Observer {
    var triggered = false
    var hook: () -> (Unit) = {}
    override fun update() {
        triggered = true
        hook()
    }
}