package saschpe.log4k

import testing.TestLoggerTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal expect val expectedListTag: String
internal expect val expectedMapTag: String

class LoggedTest : TestLoggerTest() {
    @Test
    fun logged_Pair() {
        // Arrange
        val pair = Pair("Hello", "World")

        // Act
        pair.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "(Hello, World)", "Pair", null)
    }

    @Test
    fun logged_List() {
        // Arrange
        val list = listOf("Hello", "World")

        // Act
        val returned: List<String> = list.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "[Hello, World]", "List", null)
        assertEquals(list, returned)
    }

    @Test
    fun logged_Map() {
        // Arrange
        val map = mapOf("Hello" to "World")

        // Act
        map.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "{Hello=World}", "Map", null)
    }
}
