plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.maven.publish)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())

    androidTarget { publishLibraryVariants("debug", "release") }
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    js { nodejs() }
    jvm()
    macosArm64()
    tvosArm64()
    watchosArm64()

    sourceSets {
        commonMain.dependencies { implementation(project(":log4k")) }
        commonTest.dependencies { implementation(kotlin("test")) }
        androidMain.dependencies { implementation(libs.slf4j.api) }
        jvmMain.dependencies { implementation(libs.slf4j.api) }
    }
}

android {
    namespace = "saschpe.log4k.slf4j"

    defaultConfig {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    testCoverage.jacocoVersion = libs.versions.jacoco.get()
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates("de.peilicke.sascha", name, version.toString())

    pom {
        name.set("Log4K-SLF4J")
        description.set("Lightweight logging library for Kotlin/Multiplatform - SLF4J integration. Supports Android, iOS, JavaScript and plain JVM environments.")
        url.set("https://github.com/saschpe/log4k")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("saschpe")
                name.set("Sascha Peilicke")
                email.set("sascha@peilicke.de")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/saschpe/log4k.git")
            developerConnection.set("scm:git:ssh://github.com/saschpe/log4k.git")
            url.set("https://github.com/saschpe/log4k")
        }
    }
}
