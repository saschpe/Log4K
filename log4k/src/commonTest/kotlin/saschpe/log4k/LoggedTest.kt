package saschpe.log4k

import testing.TestLoggerTest
import testing.assertTestLogger
import kotlin.test.Test

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
        list.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "[Hello, World]", expectedListTag, null)
    }

    @Test
    fun logged_Map() {
        // Arrange
        val map = mapOf("Hello" to "World")

        // Act
        map.logged()

        // Assert
        assertTestLogger(Log.Level.Debug, "{Hello=World}", expectedMapTag, null)
    }
}
