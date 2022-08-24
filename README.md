# Dicoding Fundamental Android Submission

This project is built with AndroidX library with minimum SDK 21 (Android KitKat / 5.0x) and target SDK 29 (Android 10), with `MVVM + LiveData` and `Repository` pattern using `ViewBinding`.


## Libraries

The versions of libraries are defined in project level `build.gradle`.

### Networking
* [Retrofit2](https://square.github.io/retrofit/) (com.squareup.retrofit2:retrofit), HTTP Client for fetch and send requests to servers
* [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) (com.squareup.retrofit2:converter-gson), converter which use Gson for serialization to and from JSON
* [Logging Intercepter](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) (com.squareup.okhttp3:logging-interceptor), for print log incoming and outgoing http calls

### Dependency Injection
* [Koin](https://insert-koin.io/) (org.koin:koin-android), simple DI library for Kotlin
* [Koin](https://insert-koin.io/docs/2.0/getting-started/android-viewmodel/) AndroidX ViewModel (org.koin:koin-androidx-viewmodel), binding AndroidX and ViewModel with Koin 

### Image Processor
* [Glide](https://github.com/bumptech/glide) (com.github.bumptech.glide:glide), processor for handle image processing easily and fetch image from network

### Room
* Room (androidx.room:room-runtime), for local database
* [Debug DB](https://github.com/amitshekhariitbhu/Android-Debug-Database) (com.amitshekhar.android:debug-db:1.0.6), for database debugger
