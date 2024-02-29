import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

val buildEnvironmentPropertiesFile = rootProject.file("environment.properties")
val buildEnvironmentProperties = Properties().apply {
    load(FileInputStream(buildEnvironmentPropertiesFile))
}

android {
    namespace = "com.movie.common"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("consumer-rules.pro")
    }
    flavorDimensions.add("environment")

    productFlavors {
        create("development"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", buildEnvironmentProperties["DEV_ENDPOINT"].toString())
        }
        create("beta"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", buildEnvironmentProperties["BETA_ENDPOINT"].toString())
        }
        create("production"){
            dimension = "environment"
            buildConfigField("String", "BASE_URL", buildEnvironmentProperties["PROD_ENDPOINT"].toString())
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        suppressWarnings = true
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=org.koin.core.component.KoinApiExtension",
            "-Xinline-classes",
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xallow-result-return-type"
        )
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.uiresources))

    // Kotlin
    implementation(KotlinLibraries.kotlinCoroutineCore)
    implementation(KotlinLibraries.kotlinCoroutineAndroid)

    // Android
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.activityKtx)
    implementation(AndroidLibraries.fragmentKtx)
    implementation(AndroidLibraries.material)
    implementation(AndroidLibraries.recyclerView)
    implementation(AndroidLibraries.swipeRefreshLayout)
    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.constraintLayout)
    implementation(AndroidLibraries.lifecycleCommon)
    implementation(AndroidLibraries.lifecycleLiveData)
    implementation(AndroidLibraries.lifecycleViewModel)
    implementation(AndroidLibraries.lifecycleRuntimeKtx)

    // Dagger
    implementation(Libraries.dagger)
    implementation(Libraries.daggerSupport)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)

    // KotPref
    implementation(Libraries.kotPrefCore)
    implementation(Libraries.kotPrefInit)
    implementation(Libraries.kotPrefGson)

    api(KotlinLibraries.kotlinCoroutinePlayService)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.facebookShimmer)
    api(Libraries.glide)
    api(Libraries.glideHttpIntegration)
    api(Libraries.glideTransformations)
    kapt(Libraries.glideKapt)
    implementation(Libraries.timberLog)

    // Networking
    implementation(Libraries.retrofit)
    implementation(Libraries.okhttp)
    implementation(Libraries.httpLoggingInterceptor)
    implementation(Libraries.gson)
    debugImplementation(Libraries.chuckerDebug)
    releaseImplementation(Libraries.chuckerRelease)
}