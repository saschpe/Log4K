plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.hotReload)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())

    android {
        namespace = "saschpe.log4k.demo"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        androidResources {
            enable = true
        }
    }
    jvm()
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "Log4KDemo"
            binaryOption("bundleId", "saschpe.log4k.demo")
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":log4k"))
            implementation(project(":log4k-slf4j"))
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.ui.tooling.preview)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
        }
    }
}
