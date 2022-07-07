package saschpe.log4k

import kotlin.test.Test
import kotlin.test.assertTrue

class LogLevelTest {
    @Test
    fun logLevelOrdering() {
        assertTrue(Log.Level.Verbose < Log.Level.Debug)
        assertTrue(Log.Level.Debug < Log.Level.Info)
        assertTrue(Log.Level.Info < Log.Level.Warning)
        assertTrue(Log.Level.Warning < Log.Level.Error)
        assertTrue(Log.Level.Error < Log.Level.Assert)
    }
}