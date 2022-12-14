<h1 align="center">DotPay</h1>

<p align="center">
  <a href="https://www.android.com/"><img alt="Platform" src="https://img.shields.io/badge/Platform-Android-white"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-23%2B-yellow.svg?style=flat"/></a>
  <a href="https://github.com/abdulwahabhassan/dotpay/actions"><img alt="Build Status" src="https://github.com/abdulwahabhassan/dotpay/workflows/Build/badge.svg"/></a> 
</p>

<p align="center">This is a cosmetic store app</p>

## Preview

|            |            |            |            |
|------------|------------|------------|------------|
| ![img one](img_one.png) | ![img two](img_two.png) | ![img three](img_three.png) | ![img four](img_four.png) |

## Tech stack & Third-party libraries
- [Kotlin](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous operations and background processes.
- Jetpack
  - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
  - ViewBinding - Generates binding classes for XML layout files.
  - LiveData - Lifecycle-aware observable data holder class.
  - Hilt - Manage dependency injection.
  - Glide - Image loading library
- Architecture
  - MVVM Architecture (Model - View - ViewModel) with Repository pattern
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.
- Custom Views / Animations
  - [MotionToast](https://github.com/Spikeysanju/MotionToast) - A simple and beautiful way to show animated toast messages.

## License
```xml

Designed and developed by 2022 devhassan (Hassan Abdulwahab)

Licensed under the Apache License, Version 2.0 (the "License");
You may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
