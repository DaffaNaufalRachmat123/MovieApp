# Movie App
Simple android application to fetch movies from api.themoviedb V3

## Architecture
* Modularization by features.
* Clean Architecture + MVVM.
* Dependency injection with Dagger 2. It is configured to use for both Library & Dynamic Feature Module.
* Paging 3 library

## Built With
* [Jetpack Foundation & UI](https://developer.android.com/jetpack): AndroidX, Material Design, ConstrainsLayout,...
* [JetPack Architecture](https://developer.android.com/jetpack): DataBinding, ViewModel, LiveData, Lifecycle, Navigation
* [Kotlin](https://kotlinlang.org/)
* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
* [Dagger 2](https://github.com/google/dagger) for Dependency Injection
* [Retrofit](https://github.com/square/retrofit) for HTTP client
* AndroidX
* Gson
* Glide
* Paging 3
* and More...

### Feature Modules:
* **app Module:**    Responsible for building the Application Apk.
* **common Module:**   Contains all Common components like Base Activities, Base Adapter , Extension, etc.
* **core Module:**   Contains all classes for components like Network shared preferences, main models and Network Module.
* **Features Module:**   Contains all classes for feature.


## Requirements

- Android Studio Hedgehog for Minimum version
- JBR-17 (JetBrains Runtime)
- [Android SDK](http://developer.android.com/sdk/index.html).
- Android 14 [(API 34) ](http://developer.android.com/tools/revisions/platforms.html).
- [Kotlin](https://developer.android.com/kotlin/index.html)
- Latest Android SDK Tools and build tools.


## Troubleshooting

Refer to the [Documentation section](https://developer.android.com/guide/app-bundle)
of the Android App Bundle documentation.

If you find an issue, please file a [new issue](https://github.com/android/app-bundle-samples/issues/new).

In case you have questions on App Bundle, refer to [StackOverflow](https://stackoverflow.com/questions/tagged/app-bundle).
