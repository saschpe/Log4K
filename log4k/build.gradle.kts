plugins {
    `maven-publish`
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform)
    signing
}

kotlin {
    androidTarget { publishAllLibraryVariants() }
    iosArm64()
    iosX64()
    iosSimulatorArm64()
    js { nodejs() }
    jvm()
    macosArm64()
    tvosArm64()
    watchosArm64()

    applyDefaultHierarchyTemplate()

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

java.toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

android {
    namespace = "saschpe.log4k"

    defaultConfig {
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    testCoverage.jacocoVersion = libs.versions.jacoco.get()
}

publishing {
    publications.withType<MavenPublication> {
        groupId = "de.peilicke.sascha"
        version = "1.4.1"

        artifact(project.tasks.register("${name}DokkaJar", Jar::class) {
            group = JavaBasePlugin.DOCUMENTATION_GROUP
            description = "Assembles Kotlin docs with Dokka into a Javadoc jar"
            archiveClassifier.set("javadoc")
            from(tasks.named("dokkaHtml"))
            archiveBaseName.set("${archiveBaseName.get()}-$name")
        })

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

    if (hasProperty("sonatypeUser") && hasProperty("sonatypePass")) {
        repositories {
            maven {
                name = "sonatype"
                credentials {
                    username = property("sonatypeUser") as String
                    password = property("sonatypePass") as String
                }
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            }
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
