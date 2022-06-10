package saschpe.log4k

import testing.TestLogger
import testing.assertTestLogger
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class LogTestJs {
    @BeforeTest // Arrange
    fun before() {
        Log.loggers += TestLogger()
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
        assertTestLogger(Log.Level.Debug, "{Hello=World}", "HashMap", null)
    }

    @AfterTest
    fun after() = Log.loggers.clear()
}
