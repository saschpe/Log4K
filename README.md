# Log4K
[![Build Status](https://github.com/saschpe/log4k/workflows/Main%20CI/badge.svg)](https://github.com/saschpe/log4k/actions)
[![Security](https://github.com/saschpe/Log4K/actions/workflows/security.yml/badge.svg)](https://github.com/saschpe/Log4K/actions/workflows/security.yml)
[![Maven Central](https://img.shields.io/maven-central/v/de.peilicke.sascha/log4k.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22de.peilicke.sascha%22%20AND%20a:%22log4k%22)
![Kotlin Version](https://img.shields.io/badge/Kotlin-v2.0.20-purple?style=flat&logo=kotlin)
[![GitHub license](https://img.shields.io/badge/License-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

![badge-android](http://img.shields.io/badge/Platform-Android-brightgreen.svg?logo=android)
![badge-ios](http://img.shields.io/badge/Platform-iOS-orange.svg?logo=apple)
![badge-js](http://img.shields.io/badge/Platform-NodeJS-yellow.svg?logo=javascript)
![badge-jvm](http://img.shields.io/badge/Platform-JVM-red.svg?logo=openjdk)
![badge-macos](http://img.shields.io/badge/Platform-macOS-orange.svg?logo=apple)
![badge-tvos](http://img.shields.io/badge/Platform-tvOS-orange.svg?logo=apple)
![badge-watchos](http://img.shields.io/badge/Platform-watchOS-orange.svg?logo=apple)

Lightweight logging library for Kotlin/Multiplatform. Supports Android, iOS, JavaScript and plain JVM environments.

- **log4k**: Base library, provides infrastructure and logging to the console or files with configurable rotation and retention policies.
- **log4k-slf4j**: Integration with [SLF4J](https://www.slf4j.org)

## Download
Artifacts are published to [Maven Central][maven-central]:

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("de.peilicke.sascha:log4k:1.5.0")
}
```

## Usage
Logging messages is straightforward, the **Log** object provides the usual functions you'd expect:

```kotlin
// Log to your heart's content
Log.verbose("FYI")
Log.debug("Debugging ${foo.bar}")
Log.info("Nice to know", tag = "SomeClass")
Log.warn("Warning about $stuff ...")
Log.error("Oops!")
Log.assert("Something went wrong!", throwable)
```

Or, if you prefer:

```kotlin
Log.verbose { "FYI" }
Log.debug { "Debugging ${foo.bar}" }
Log.info(tag = "SomeClass") { "Nice to know" }
Log.warn { "Warning about $stuff ..." }
Log.error { "Oops!" }
Log.assert(throwable = Exception("Ouch!")) { "Something went wrong!" }
```

The log output includes the function name and line and pretty-prints exceptions on all supported platforms:

    I/Application.onCreate: Log4K rocks!

### Logging objects
In case you want to log `Any` Kotlin class instance or object:

```kotlin
val map = mapOf("Hello" to "World")
map.logged()
```

The above example logs `{Hello=World}` with the tag `SingletonMap` with the log level `Debug`.

### Logging expensive results
Sometimes, the log output involves a heavy computation that is not always necessary. For example, if the global log
level is set to `Info` or above, the following text would not appear in any log output:

```kotlin
Log.debug("Some ${veryHeavyStuff()}")
```

However, the function `veryHeavyStuff()` will be executed regardless. To avoid this, use:

```kotlin
Log.debug { "Some ${veryHeavyStuff()}" }
```

## Configuration (Android example)
To only output messages with log-level *info* and above, you can configure the console logger in your Application class:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.loggers.forEach {
            if (!BuildConfig.DEBUG) {
                it.minimumLogLevel = Log.Level.Info
            }
        }
    }
}
```

## Logging to a file

By default, the library only logs to the current platform's console.
Additionally or instead, add one or multiple file loggers:

```kotlin
// Log with daily rotation and keep five log files at max:
Log.loggers += FileLogger(rotate = Rotate.Daily, limit = Limit.Files(max = 5))

// Log to a custom path and rotate every 1000 lines written:
Log.loggers += FileLogger(rotate = Rotate.After(lines = 1000), logPath = "myLogPath")

// Log with sensible defaults (daily, keep 10 files)
Log.loggers += FileLogger()

// One huge eternal log file:
Log.loggers += FileLogger(rotate = Rotate.Never, limit = Limit.Not)
```

## Custom logger (Android Crashlytics example)
The library provides a cross-platform `ConsoleLogger` by default. Custom loggers can easily be added. For instance, to
send only `ERROR` and `ASSERT` messages to Crashlytics in production builds, you could do the following:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.loggers.clear() // Remove default loggers
        Log.loggers += when {
            BuildConfig.DEBUG -> ConsoleLogger()
            else -> CrashlyticsLogger()
        }
    }

    private class CrashlyticsLogger : Logger() {
        override fun print(level: Log.Level, tag: String, message: String?, throwable: Throwable?) {
            val priority = when (level) {
                Log.Level.Verbose -> VERBOSE
                Log.Level.Debug -> DEBUG
                Log.Level.Info -> INFO
                Log.Level.Warning -> WARN
                Log.Level.Error -> ERROR
                Log.Level.Assert -> ASSERT
            }
            if (priority >= ERROR) {
                FirebaseCrashlytics.getInstance().log("$priority $tag $message")
                throwable?.let { FirebaseCrashlytics.getInstance().recordException(it) }
            }
        }
    }
}
```

## Ktor integration

Ktor supports [providing a custom logger][ktor-logging]:

```kotlin
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import saschpe.log4k.Log

val httpClient = HttpClient(CIO) {
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) = Log.info { message }
        }
    }
}
```

## Users

- [Alpha+ Player — Unofficial player for Soma FM](https://play.google.com/store/apps/details?id=saschpe.alphaplus)
- [GameOn — Get games on sale](https://play.google.com/store/apps/details?id=saschpe.gameon)

## License

    Copyright 2019 Sascha Peilicke

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[ktor-logging]: https://ktor.io/docs/client-logging.html#custom_logger

[maven-central]: https://search.maven.org/artifact/de.peilicke.sascha/android-customtabs
