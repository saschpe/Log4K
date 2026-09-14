plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.versions)
}

spotless {
    freshmark {
        target("**/*.md")
        propertiesFile("gradle.properties")
        replaceRegex(
            "Log4K dependency version",
            "de\\.peilicke\\.sascha:log4k:[^\"\\s)]+",
            "de.peilicke.sascha:log4k:${project.version}",
        )
        replaceRegex(
            "Kotlin version badge",
            "https://img\\.shields\\.io/badge/Kotlin-v[^?]+",
            "https://img.shields.io/badge/Kotlin-v${libs.versions.kotlin.get()}-purple",
        )
    }
    kotlin {
        target("**/*.kt")
        ktlint(libs.versions.ktlint.get()).setEditorConfigPath("${project.rootDir}/.editorconfig")
    }
    kotlinGradle {
        ktlint(libs.versions.ktlint.get()).setEditorConfigPath("${project.rootDir}/.editorconfig")
    }
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        fun isStable(version: String) = Regex("^[0-9,.v-]+(-r)?$").matches(version)
        !isStable(candidate.version) && isStable(currentVersion)
    }
}
