# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
- Dependency update:
  - [Gradle-7.5](https://docs.gradle.org/7.5/release-notes.html)

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
