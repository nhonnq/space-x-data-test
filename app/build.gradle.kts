import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    if (!keystorePropertiesFile.exists()) {
        logger.warn("Release builds may not work: signing config not found.")
        return@android
    }
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    compileSdk = Configs.compileSdkVersion
    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        renderscriptSupportModeEnabled = true
        multiDexEnabled = true

        testInstrumentationRunner = Configs.testInstrumentationRunner

    }

    signingConfigs {
        getByName("debug") {
            keyAlias = keystoreProperties["KEY_ALIAS"] as String
            keyPassword = keystoreProperties["KEY_PASSWORD"] as String
            storeFile = rootProject.file(keystoreProperties["STORE_FILE"] as String)
            storePassword = keystoreProperties["STORE_PASSWORD"] as String
        }
        create("release") {
            keyAlias = keystoreProperties["KEY_ALIAS"] as String
            keyPassword = keystoreProperties["KEY_PASSWORD"] as String
            storeFile =  rootProject.file(keystoreProperties["STORE_FILE"] as String)
            storePassword = keystoreProperties["STORE_PASSWORD"] as String
        }
    }

    buildTypes {
        // Development ENV
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BASE_API_URL", "\"https://api.spacexdata.com/\"")
        }
        // Production ENV
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            // Change API URL for Production ENV if needed
            buildConfigField("String", "BASE_API_URL", "\"https://api.spacexdata.com/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    configurations.all {
        resolutionStrategy {
            exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-debug")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/DEPENDENCIES"
            excludes += "/META-INF/LICENSE"
            excludes += "/META-INF/LICENSE.txt"
            excludes += "/META-INF/license.txt"
            excludes += "/META-INF/NOTICE"
            excludes += "/META-INF/NOTICE.txt"
            excludes += "/META-INF/notice.txt"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Appcompat
    implementation(Dependencies.appcompat)

    // Kotlin, Core
    implementation(Dependencies.kotlin)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.material)
    implementation(Dependencies.activityKtx)

    // Layout
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.recyclerview)
    implementation(Dependencies.cardview)
    implementation(Dependencies.swipeRefreshLayout)

    // Multidex
    implementation(Dependencies.multidex)

    // Dagger Hilt
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)

    // Coroutines
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    // Gson, Retrofit, OKHTTP
    implementation(Dependencies.gson)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okHttpLoggingInterceptor)
    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.retrofitRxJava)

    // Lifecycle
    implementation(Dependencies.lifecycleRumtime)
    kapt(Dependencies.lifecycleCompiler)

    // Room database
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)

    // Glide load image with caching flow
    implementation(Dependencies.glide)
    kapt(Dependencies.glideCompiler)

    // Testing
    testImplementation(Dependencies.junit4)
    testImplementation(Dependencies.junitExtensionsKtx)
    testImplementation(Dependencies.coroutinesTest)
    androidTestImplementation(Dependencies.junitExtensions)
    androidTestImplementation(Dependencies.espressoCore)
}
