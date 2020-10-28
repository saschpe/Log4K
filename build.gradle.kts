buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

plugins {
    id("com.diffplug.spotless") version "5.7.0"
    id("com.github.ben-manes.versions") version "0.33.0"
    kotlin("jvm") version "1.4.10"
}

repositories {
    jcenter()
}

spotless {
    format("misc") {
        target("**/*.gradle", "*.md", "**/.gitignore")
        trimTrailingWhitespace()
        endWithNewline()
    }
    freshmark {
        target("*.md")
        propertiesFile("gradle.properties")
    }
    kotlin {
        target("*/src/**/*.kt")
        ktlint().userData(mapOf("disabled_rules" to "no-wildcard-imports"))
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        fun isStable(version: String) = Regex("^[0-9,.v-]+(-r)?$").matches(version)

        !isStable(candidate.version) && isStable(currentVersion)
    }
}
