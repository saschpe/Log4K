# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.5.1] - 2025-09-24

## [1.5.0] - 2025-06-26

## [1.4.1] - 2025-01-30

## [1.4.0] - 2024-09-27

## [1.3.5] - 2024-09-12
- Dependency updates:
  - [kotlinx-io 0.5.3](https://github.com/Kotlin/kotlinx-io/releases/tag/0.5.3)
  - [kotlin-wrappers pre.805](https://github.com/JetBrains/kotlin-wrappers/releases/tag/pre.805)
  - [androidx-activity-compose 1.9.2](https://developer.android.com/jetpack/androidx/releases/activity#1.9.2)
  - [Kotlin 2.0.20](https://github.com/JetBrains/kotlin/releases/tag/v2.0.20)

## [1.3.4] - 2024-08-09
- Dependency updates:
  - [kotlinx-io 0.5.1](https://github.com/Kotlin/kotlinx-io/releases/tag/0.5.1)
  - [kotlin-wrappers pre.788](https://github.com/JetBrains/kotlin-wrappers/releases/tag/pre.788)
  - [androidx-activity-compose 1.9.1](https://developer.android.com/jetpack/androidx/releases/activity#1.9.1)
  - [Kotlin 2.0.10](https://github.com/JetBrains/kotlin/releases/tag/v2.0.10)
- Android: Relax minimum SDK requirement to API level 21

## [1.3.3] - 2024-07-11
- iOS: Better log tag and use NSLog

## [1.3.2] - 2024-06-19
- Use more unique (internal) content provider authorities. Avoids potential clashes in apps integrating the library
- Dependency update:
  - [Gradle 8.8](https://docs.gradle.org/8.8/release-notes.html)
  - [Kotlin 2.0.0](https://github.com/JetBrains/kotlin/releases/tag/v2.0.0)
  - [kotlinx-io 0.4.0](https://github.com/Kotlin/kotlinx-io/releases/tag/0.4.0)
  - [kotlin-wrappers pre.757](https://github.com/JetBrains/kotlin-wrappers/releases/tag/pre.757)
- Add iosX64 build target

## [1.3.1] - 2024-05-22
- FileLogger: Remove constructor with `kotlinx.io.files.Path`
  Avoids having to add kotlinx-io as a dependency when using the class

## [1.3.0] - 2024-05-02
- Provide FileLogger for persistent logging to a file path with rotation and retention policy support.

## [1.2.5] - 2024-03-23
- Provide javadoc artifacts for Sonatype Maven Central

## [1.2.4] - 2024-03-23
### Changed
- Allow empty log messages if you only want to create a log entry about a function being called.
- Add more Apple ARM64 platforms: macOS, tvOS, watchOS
- Provide FileLogger in addition to ConsoleLogger
- Dependency update:
  - [Kotlin 1.9.23](https://kotlinlang.org/docs/whatsnew19.html)
  - [Gradle-8.7](https://docs.gradle.org/8.7/release-notes.html)
  - [Android Gradle Plugin 8.2.2](https://developer.android.com/build/releases/past-releases/agp-8-2-0-release-notes)

## [1.2.3] - 2022-10-12
### Changed
- Android: Target API level 33 (Android 13)
- Dependency update:
  - [Android Gradle Plugin 7.3.0](https://developer.android.com/studio/releases/gradle-plugin#7-3-0)
  - [Gradle 7.5.1](https://docs.gradle.org/7.5.1/release-notes.html)
  - [Kotlin 1.7.20](https://kotlinlang.org/docs/whatsnew1720.html)
### Removed
- Stop building frameworks, they aren't published by default

## [1.2.2] - 2022-07-11

- Added SLF4J `MDC` wrapper

## [1.2.1] - 2022-07-11

- New library `log4k-slf4j`:
  - `SLF4JLogger`: Forwards log statements to [SLF4J](https://www.slf4j.org), which in turn allows to
    use [Logback](https://logback.qos.ch) as a backend.

## [1.2.0] - 2022-07-11

- Use `ConsoleLogger` by default

## [1.1.3] - 2022-07-07

- Add method `Log.isDebugEnabled` identical to SLF4J's `LOG.isDebugEnabled()`.
- Dependency update:
  - [Kotlin 1.7.10](https://github.com/JetBrains/kotlin/releases/tag/v1.7.10)
