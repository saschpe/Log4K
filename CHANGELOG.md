# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
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
    use [Logback])(https://logback.qos.ch) as a backend.

## [1.2.0] - 2022-07-11

- Use `ConsoleLogger` by default

## [1.1.3] - 2022-07-07

- Add method `Log.isDebugEnabled` identical to SLF4J's `LOG.isDebugEnabled()`.
- Dependency update:
  - [Kotlin 1.7.10](https://github.com/JetBrains/kotlin/releases/tag/v1.7.10)
