@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
    }

    plugins {
        kotlin("multiplatform") version "2.0.0"
        id("com.android.library") version "8.2.2"
        id("org.jetbrains.dokka") version "1.9.20"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
    }
}

rootProject.name = "Log4K"

include(":log4k")
include(":log4k-slf4j")
