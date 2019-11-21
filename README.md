# Log4K
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Download](https://api.bintray.com/packages/saschpe/maven/log4k/images/download.svg?version=0.1.2)](https://bintray.com/saschpe/maven/log4k/0.1.2/link)
[![Build Status](https://github.com/saschpe/log4k/workflows/Main%20CI/badge.svg)](https://github.com/saschpe/log4k/actions)
![Kotlin Version](https://img.shields.io/badge/kotlin-v1.3.60-F88909?style=flat&logo=kotlin)

Lightweight logging library for Kotlin/Multiplatform. Supports Android, iOS,
JavaScript and plain JVM environments.

# Download
```kotlin
compile("saschpe.log4k:log4k:0.1.2")
```

# Usage
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

# Configuration (Android example)
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

Snapshots of the development version are available in [Sonatype's `snapshots` repository][sonatype].

# Users
 - [Alpha+ Player](https://play.google.com/store/apps/details?id=saschpe.alphaplus)
 - [GameOn](https://play.google.com/store/apps/details?id=saschpe.gameon)

# License

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


 [sonatype]: https://oss.sonatype.org/content/repositories/snapshots/
