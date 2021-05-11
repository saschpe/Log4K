plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(30)
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
    }

    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

group = "de.peilicke.sascha"
version = "1.0.0"

kotlin {
    android {
        compilations.all { kotlinOptions.jvmTarget = "1.8" }
        publishAllLibraryVariants()
    }
    iosArm64 { binaries.framework("Log4K") }
    iosX64 { binaries.framework("Log4K") }
    js {
        browser()
        compilations.all {
            kotlinOptions.sourceMap = true
            kotlinOptions.moduleKind = "umd"
        }
    }
    jvm { compilations.all { kotlinOptions.jvmTarget = "1.8" } }

    sourceSets {
        named("commonMain") { }
        named("commonTest") {
            dependencies {
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-common"))
                implementation("io.mockk:mockk-common:1.11.0")
            }
        }

        named("androidMain") {
        }
        named("androidTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("androidx.test:core-ktx:1.3.0")
                implementation("androidx.test.ext:junit-ktx:1.1.2")
                implementation("io.mockk:mockk:1.11.0")
            }
        }

        val iosMain by creating {
            dependencies { }
        }
        val iosTest by creating {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        getByName("iosArm64Main") { dependsOn(iosMain) }
        getByName("iosArm64Test") { dependsOn(iosTest) }
        getByName("iosX64Main") { dependsOn(iosMain) }
        getByName("iosX64Test") { dependsOn(iosTest) }

        named("jsMain") { }
        // Note: mockk is not available for JavaScript
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
                implementation("io.mockk:mockk-dsl-js:1.11.0")
            }
        }

        named("jvmMain") { }
        named("jvmTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("io.mockk:mockk:1.11.0")
            }
        }
    }

    tasks {
        register("universalFrameworkDebug", org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask::class) {
            baseName = "Log4K"
            from(
                iosArm64().binaries.getFramework("Log4K", "Debug"),
                iosX64().binaries.getFramework("Log4K", "Debug")
            )
            destinationDir = buildDir.resolve("bin/universal/debug")
            group = "Universal framework"
            description = "Builds a universal (fat) debug framework"
            dependsOn("linkLog4KDebugFrameworkIosArm64")
            dependsOn("linkLog4KDebugFrameworkIosX64")
        }

        register("universalFrameworkRelease", org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask::class) {
            baseName = "Log4K"
            from(
                iosArm64().binaries.getFramework("Log4K", "Release"),
                iosX64().binaries.getFramework("Log4K", "Release")
            )
            destinationDir = buildDir.resolve("bin/universal/release")
            group = "Universal framework"
            description = "Builds a universal (fat) release framework"
            dependsOn("linkLog4KReleaseFrameworkIosArm64")
            dependsOn("linkLog4KReleaseFrameworkIosX64")
        }

        register("universalFramework") {
            dependsOn("universalFrameworkDebug")
            dependsOn("universalFrameworkRelease")
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        pom {
            name.set("Log4K")
            description.set("Lightweight logging library for Kotlin/Multiplatform. Supports Android, iOS, JavaScript and plain JVM environments.")
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
    useGpgCmd()
    sign(publishing.publications)
}
