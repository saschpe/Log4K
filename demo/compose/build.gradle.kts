plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())

    androidTarget()
    jvm()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
        iosX64(),
    ).forEach {
        it.binaries.framework {
            baseName = "Log4KDemo"
            isStatic = true
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(project(":log4k"))
            implementation(project(":log4k-slf4j"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.slf4j.simple)
        }
    }
}

android {
    namespace = "saschpe.log4k.demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "saschpe.log4k.demo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = libs.versions.log4k.demo.get()
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
            )
            packageName = "saschpe.log4k.demo"
            packageVersion = libs.versions.log4k.demo.get()
        }
    }
}
