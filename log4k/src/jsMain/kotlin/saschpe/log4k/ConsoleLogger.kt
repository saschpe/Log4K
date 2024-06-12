package saschpe.log4k

actual class ConsoleLogger : Logger() {
    actual override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
        var fullMessage = "$message"
        throwable?.let { fullMessage = "$fullMessage\n${it.message}" }

        when (level) {
            Log.Level.Verbose -> console.log("Verbose $tag: $fullMessage")
            Log.Level.Debug -> console.log("Debug $tag: $fullMessage")
            Log.Level.Info -> console.info("Info $tag: $fullMessage")
            Log.Level.Warning -> console.warn("Warning $tag: $fullMessage")
            Log.Level.Error -> console.error("Error $tag: $fullMessage")
            Log.Level.Assert -> console.error("Assert $tag: $fullMessage")
        }
    }
}
