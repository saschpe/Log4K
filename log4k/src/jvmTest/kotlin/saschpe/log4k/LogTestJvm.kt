package saschpe.log4k

import testing.TestLogger
import testing.assertTestLogger
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class LogTestJvm {
    @BeforeTest // Arrange
    fun before() {
        Log.loggers.add(TestLogger())
    }

    @Test
    fun logged_List() {
        // Arrange
        val list = listOf("Hello", "World")

        // Act
        list.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "[Hello, World]", "ArrayList", null)
    }

    @Test
    fun logged_Map() {
        // Arrange
        val map = mapOf("Hello" to "World")

        // Act
        map.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "{Hello=World}", "SingletonMap", null)
    }

    @AfterTest
    fun after() = Log.loggers.clear()
}
