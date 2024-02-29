import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

val buildEnvironmentPropertiesFile = rootProject.file("environment.properties")
val buildEnvironmentProperties = Properties().apply {
    load(FileInputStream(buildEnvironmentPropertiesFile))
}

android {
    namespace = "com.movie.features.splash"
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // Modules
    implementation(project(Modules.core))
    implementation(project(Modules.common))
    implementation(project(Modules.uiresources))
    implementation(project(Modules.navigation))

    // Android Libraries
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.fragmentKtx)
    implementation(AndroidLibraries.material)
    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.lifecycleCommon)
    implementation(AndroidLibraries.lifecycleExtensions)
    implementation(AndroidLibraries.lifecycleLiveData)
    implementation(AndroidLibraries.lifecycleViewModel)
    implementation(AndroidLibraries.lifecycleRuntimeKtx)

    // Libraries
    implementation(Libraries.timberLog)
    implementation(Libraries.paging3)
    implementation(Libraries.BRVAH)

    // API
    api(Libraries.kotPrefInit)
    api(Libraries.kotPrefCore)
    api(Libraries.kotPrefGson)
    api(Libraries.retrofit)
    api(Libraries.retrofitGsonConverter)
    api(Libraries.okhttp)
    api(Libraries.httpLoggingInterceptor)
    api(Libraries.gson)

    // Dagger
    implementation(Libraries.dagger)
    implementation(Libraries.daggerSupport)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)

    // Test Libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}