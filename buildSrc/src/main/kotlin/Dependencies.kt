
object Libraries {

    // Timber for logging
    val timberLog                   = "com.github.ajalt:timberkt:1.5.1"

    // Dagger
    val dagger                      = "com.google.dagger:dagger:2.49"
    val daggerCompiler              = "com.google.dagger:dagger-compiler:2.49"

    val daggerSupport               = "com.google.dagger:dagger-android-support:2.49"
    val daggerProcessor             = "com.google.dagger:dagger-android-processor:2.49"

    // Shimmer
    val facebookShimmer             = "com.facebook.shimmer:shimmer:0.5.0"
    // Retrofit and OkHttp for networking
    val gson                        = "com.google.code.gson:gson:2.10.1"
    val retrofit                    = "com.squareup.retrofit2:retrofit:2.9.0"
    val retrofitGsonConverter       = "com.squareup.retrofit2:converter-gson:2.9.0"
    val okhttp                      = "com.squareup.okhttp3:okhttp:4.11.0"
    val httpLoggingInterceptor      = "com.squareup.okhttp3:logging-interceptor:4.11.0"

    val chuckerDebug                = "com.github.ChuckerTeam.Chucker:library:3.4.0"
    val chuckerRelease              = "com.github.ChuckerTeam.Chucker:library-no-op:3.4.0"

    // ReactiveX for Reactive Programming
    val rxJava                      = "io.reactivex.rxjava3:rxjava:3.0.9"
    val rxKotlin                    = "io.reactivex.rxjava3:rxkotlin:3.0.1"
    val rxAndroid                   = "io.reactivex.rxjava3:rxandroid:3.0.0"

    // Glide for Image Loader
    const val glide                 = "com.github.bumptech.glide:glide:4.16.0"
    const val glideHttpIntegration  = "com.github.bumptech.glide:okhttp3-integration:4.16.0"
    const val glideKapt             = "com.github.bumptech.glide:compiler:4.16.0"
    const val glideTransformations  = "jp.wasabeef:glide-transformations:4.3.0"
    //Shadow
    val shadowDrawable              = "com.github.BluRe-CN:ComplexView:v1.1"

    // another dependencies
    val kotPrefCore                 = "com.chibatching.kotpref:kotpref:2.13.0"
    val kotPrefInit                 = "com.chibatching.kotpref:initializer:2.13.0"
    val kotPrefGson                 = "com.chibatching.kotpref:gson-support:2.13.0"
    val BRVAH                       = "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.50"//2.9.50

    // Paging3 Library
    val paging3                     = "androidx.paging:paging-runtime-ktx:3.2.1"

    val youtubePlayer               = "com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5"
}

object KotlinLibraries {
    val kotlinCoroutineCore         = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3"
    val kotlinCoroutineAndroid      = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    val kotlinCoroutinePlayService  = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3"
}

object AndroidLibraries {
    // ANDROID
    val appCompat                   = "androidx.appcompat:appcompat:1.6.1"
    val activityKtx                 = "androidx.activity:activity-ktx:1.8.2"
    val fragmentKtx                 = "androidx.fragment:fragment-ktx:1.6.2"
    val material                    = "com.google.android.material:material:1.11.0"
    val recyclerView                = "androidx.recyclerview:recyclerview:1.0.0"
    val legacySupport               = "androidx.legacy:legacy-support-v4:1.0.0"
    val swipeRefreshLayout          = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    val cardView                    = "androidx.cardview:cardview:1.0.0"
    val coreKtx                     = "androidx.core:core-ktx:1.12.0"
    val constraintLayout            = "androidx.constraintlayout:constraintlayout:2.1.4"
    val multiDex                    = "androidx.multidex:multidex:2.0.1"

    val lifecycleViewModel          = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
    val lifecycleExtensions         = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    val lifecycleRuntimeKtx         = "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
    val lifecycleCommon             = "androidx.lifecycle:lifecycle-common-java8:2.7.0"
    val lifecycleLiveData           = "androidx.lifecycle:lifecycle-livedata-ktx:2.7.0"
}

object TestLibraries {
    val junit                       = "junit:junit:4.13.1"
    val mockito                     = "org.mockito:mockito-core:3.12.4"
    val archCoreTesting             = "androidx.arch.core:core-testing:2.1.0"
    val mockk                       = "io.mockk:mockk:1.9.3"
}