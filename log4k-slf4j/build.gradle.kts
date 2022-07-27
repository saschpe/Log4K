plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
    signing
}

kotlin {
    android { publishAllLibraryVariants() }
    ios { binaries.framework("Log4K-SLF4J") }
    iosSimulatorArm64 { binaries.framework("Log4K-SLF4J") }
    js {
        nodejs()
        compilations.all {
            kotlinOptions.sourceMap = true
            kotlinOptions.moduleKind = "umd"
        }
    }
    jvm { testRuns["test"].executionTask.configure { useJUnitPlatform() } }
    linuxX64()
    macosArm64 { binaries.framework("Log4K-SLF4J") }
    mingwX64() // Winwhat?!?
    tvos { binaries.framework("Log4K-SLF4J") }
    watchos { binaries.framework("Log4K-SLF4J") }

    sourceSets["androidMain"].dependencies {
        implementation("org.slf4j:slf4j-api:1.7.36")
    }
    sourceSets["commonMain"].dependencies {
        implementation(project(":log4k"))
    }
    sourceSets["commonTest"].dependencies {
        implementation(kotlin("test"))
    }
    sourceSets["iosSimulatorArm64Main"].dependsOn(sourceSets["iosMain"])
    sourceSets["iosSimulatorArm64Test"].dependsOn(sourceSets["iosTest"])
    sourceSets["jvmMain"].dependencies {
        implementation("org.slf4j:slf4j-api:1.7.36")
    }

    sourceSets { // https://issuetracker.google.com/issues/152187160
        remove(sourceSets["androidAndroidTestRelease"])
        remove(sourceSets["androidTestFixtures"])
        remove(sourceSets["androidTestFixturesDebug"])
        remove(sourceSets["androidTestFixturesRelease"])
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithSimulatorTests::class.java) {
        testRuns["test"].deviceId = "iPhone 13"
    }
}

android {
    buildToolsVersion = "33.0.0"
    compileSdk = 32

    defaultConfig {
        minSdk = 17
        targetSdk = 32
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    testCoverage.jacocoVersion = "0.8.8"
}

group = "de.peilicke.sascha"
version = "1.2.2"

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

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

    repositories {
        maven {
            name = "sonatype"
            credentials {
                username = Secrets.Sonatype.user
                password = Secrets.Sonatype.apiKey
            }
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
        }
    }
}

signing {
    val sonatypeGpgKey = System.getenv("SONATYPE_GPG_KEY")
    val sonatypeGpgKeyPassword = System.getenv("SONATYPE_GPG_KEY_PASSWORD")
    when {
        sonatypeGpgKey == null || sonatypeGpgKeyPassword == null -> useGpgCmd()
        else -> useInMemoryPgpKeys(sonatypeGpgKey, sonatypeGpgKeyPassword)
    }
    sign(publishing.publications)
}