import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

val buildEnvironmentPropertiesFile = rootProject.file("environment.properties")
val buildEnvironmentProperties = Properties().apply {
    load(FileInputStream(buildEnvironmentPropertiesFile))
}

android {
    namespace = ApplicationId.id
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = ApplicationId.id
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        create("release"){
            keyAlias = "movie"
            keyPassword = "movie123"
            storeFile = file("movie.keystore")
            storePassword = "movie123"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
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
    android.applicationVariants.all {
        val variant = this
        variant.outputs.map { it as BaseVariantOutputImpl }
            .forEach { output ->
                val dateFormat = Date().time.toString()
                val outputFileName = "MovieApp-${variant.flavorName}-$dateFormat.apk"
                output.outputFileName = outputFileName
            }
        val aptOutputDir = File(buildDir, "generated/source/kapt/${unitTestVariant.dirName}")
        unitTestVariant.addJavaSourceFoldersToModel(aptOutputDir)
    }
    packaging {
        resources {
            excludes += listOf(
                "META-INF/maven/com.google.guava/guava/pom.xml",
                "META-INF/maven/com.google.guava/guava/pom.properties",
                "lib/*/libRSSupport.so",
                "lib/*/librsjni.so",
                "lib/*/librsjni_androidx.so",
                "lib/*/libblasV8.so",
                "lib/*/libconceal.so",
                "lib/*/libconcealjni.so",
                "lib/*/libfb.so",
                "META-INF/DEPENDENCIES.txt",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "LICENSE.txt",
                "META-INF/rxjava.properties",
                "META-INF/DEPENDENCIES",
                "META-INF/notice.txt",
                "META-INF/license.txt",
                "META-INF/dependencies.txt",
                "META-INF/LGPL2.1",
                "ASL-2.0.txt",
                "META-INF/ASL-2.0.txt",
                "META-INF/LGPL-3.0.txt",
                "META-INF/services/javax.annotation.processing.Processors",
                "META-INF/app_debug.kotlin_module"
            )
            with(pickFirsts){
                add("protobuf.meta")
                add("META-INF/kotlinx-io.kotlin_module")
                add("META-INF/atomicfu.kotlin_module")
                add("META-INF/kotlinx-coroutines-io.kotlin_module")
                add("META-INF/kotlinx-coroutines-core.kotlin_module")
                add("META-INF/kotlinx-serialization-runtime.kotlin_module")
            }
            jniLibs.keepDebugSymbols.add("*/mips/*.so")
            jniLibs.keepDebugSymbols.add("*/mips64/*.so")
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
        viewBinding = true
        buildConfig = true
    }
    testOptions {
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

dependencies {

    // Core Modules
    implementation(project(Modules.core))

    // Common
    implementation(project(Modules.common))

    // Navigation Modules
    implementation(project(Modules.navigation))

    // Features Modules
    implementation(project(Modules.featureMain))
    implementation(project(Modules.featuresSplash))

    // Kotlin Coroutine
    implementation(KotlinLibraries.kotlinCoroutineCore)
    implementation(KotlinLibraries.kotlinCoroutineAndroid)

    implementation(Libraries.timberLog)

    implementation(AndroidLibraries.multiDex)
    implementation(AndroidLibraries.appCompat)

    // ANDROID
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.fragmentKtx)
    implementation(AndroidLibraries.constraintLayout)
    implementation(AndroidLibraries.recyclerView)
    implementation(AndroidLibraries.cardView)

    //Paging 3
    implementation(Libraries.paging3)

    // KotPref
    implementation(Libraries.kotPrefCore)
    implementation(Libraries.kotPrefInit)
    implementation(Libraries.kotPrefGson)
    implementation(Libraries.gson)

    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConverter)

    // Dagger
    implementation(Libraries.dagger)
    implementation(Libraries.daggerSupport)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerProcessor)

    debugImplementation(Libraries.chuckerDebug)
    releaseImplementation(Libraries.chuckerRelease)

    api(Libraries.glide)
    api(Libraries.glideHttpIntegration)
    api(Libraries.glideTransformations)
    kapt(Libraries.glideKapt)
}