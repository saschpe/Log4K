plugins {
    alias(libs.plugins.android.application) apply false // Plugin does not allow being loaded multiple times
    alias(libs.plugins.android.library) apply false // Plugin does not allow being loaded multiple times
    alias(libs.plugins.kotlin.android) apply false // Plugin does not allow being loaded multiple times
    alias(libs.plugins.kotlin.multiplatform) apply false // Plugin does not allow being loaded multiple times
    alias(libs.plugins.spotless)
    alias(libs.plugins.versions)
}

spotless {
    freshmark {
        target("**/*.md")
        propertiesFile("gradle.properties")
    }
    kotlin {
        target("**/*.kt")
        ktlint("1.3.1").setEditorConfigPath("${project.rootDir}/.editorconfig")
    }
    kotlinGradle {
        ktlint("1.3.1").setEditorConfigPath("${project.rootDir}/.editorconfig")
    }
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        fun isStable(version: String) = Regex("^[0-9,.v-]+(-r)?$").matches(version)
        !isStable(candidate.version) && isStable(currentVersion)
    }
}
