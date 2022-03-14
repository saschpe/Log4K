# Log4K
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![Maven Central](https://img.shields.io/maven-central/v/de.peilicke.sascha/log4k)
[![Build Status](https://github.com/saschpe/log4k/workflows/Main%20CI/badge.svg)](https://github.com/saschpe/log4k/actions)
![badge-android](http://img.shields.io/badge/platform-android-brightgreen.svg?style=flat)
![badge-native](http://img.shields.io/badge/platform-native-lightgrey.svg?style=flat)
![badge-js](http://img.shields.io/badge/platform-js-yellow.svg?style=flat)
![badge-jvm](http://img.shields.io/badge/platform-jvm-orange.svg?style=flat)
![Kotlin Version](https://img.shields.io/badge/kotlin-v1.3.60-F88909?style=flat&logo=kotlin)

Lightweight logging library for Kotlin/Multiplatform. Supports Android, iOS,
JavaScript and plain JVM environments.

## Download
Artifacts are published to [Maven Central][maven-central]:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("de.peilicke.sascha:log4k:1.0.2")
}
```

## Usage
Logging messages is straightforward, the **Log** object provides the usual
functions you'd expect:

```kotlin
Log.verbose("FYI")
Log.debug("Debugging ${foo.bar}")
Log.info("Nice to know")
Log.warn("Warning about $stuff ...")
Log.error("Oops!")
Log.assert("Something went wrong!", throwable)
```

The log output includes the function name and line and pretty-prints exceptions
on all supported platforms:

    I/Application.onCreate: Log4K rocks!

## Configuration (Android example)
To only output messages with log-level *info* and above, you can configure the
console logger in your Application class:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.loggers.add(ConsoleLogger().apply {
            if (!BuildConfig.DEBUG) {
                minimumLogLevel = Log.Level.Info
            }
        })
    }
}
```

## Custom logger (Android Crashlytics example)
The library provides a cross-platform `ConsoleLogger` by default. Custom
loggers can easily be added. For instance, to send only `ERROR` and `ASSERT`
messages to Crashlytics in production builds, you could do the following:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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

## Users
- [Alpha+ Player - Unofficial player for Soma FM](https://play.google.com/store/apps/details?id=saschpe.alphaplus)
- [GameOn - Get games on sale](https://play.google.com/store/apps/details?id=saschpe.gameon)

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

[maven-central]: https://search.maven.org/artifact/de.peilicke.sascha/android-customtabs
