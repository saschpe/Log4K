plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

tasks {
    val ensureSecretsExist by registering {
        val secretFile = File("$rootDir/src/main/kotlin/Secrets.kt")
        description = "Ensures that $secretFile exists"

        doFirst {
            if (!secretFile.exists()) {
                secretFile.writeText(
                    """
object Secrets {
    object Bintray {
        const val username = ""
        const val password = ""
    }
}

""".trimIndent()
                )
            }
        }
    }
    named("assemble") { dependsOn(ensureSecretsExist) }
}
