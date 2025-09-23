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
    iosSimulatorArm64()
    iosX64()
    js { nodejs() }
    jvm()
    macosArm64()
    tvosArm64()
    watchosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.io)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jsMain.dependencies {
            implementation(libs.kotlin.wrappers)
        }
        jsTest.dependencies {
            implementation(kotlin("test-js"))
        }
        jvmTest.dependencies {
            implementation(libs.mockk)
        }
        androidUnitTest.dependencies {
            implementation(libs.mockk.android)
        }
    }
}

android {
    namespace = "saschpe.log4k"

    defaultConfig {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    testCoverage.jacocoVersion = libs.versions.jacoco.get()
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("de.peilicke.sascha", name, version.toString())

    pom {
        name.set("Log4K")
        description.set("Lightweight logging library for Kotlin/Multiplatform. Supports Android, iOS, JavaScript and plain JVM environments.")
        inceptionYear.set("2019")
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
