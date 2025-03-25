plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "io.proxima.breathe"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.proxima.breathe"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "io.proxima.breathe.HiltTestRunner"
        vectorDrawables.useSupportLibrary = true
    }

    // ✅ Signing config in Kotlin DSL
    signingConfigs {
        create("release") {
            storeFile = file(properties["RELEASE_STORE_FILE"] as String)
            storePassword = properties["RELEASE_STORE_PASSWORD"] as String
            keyAlias = properties["RELEASE_KEY_ALIAS"] as String
            keyPassword = properties["RELEASE_KEY_PASSWORD"] as String
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    // GMS
    implementation(libs.gms.location)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.coil)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Media3
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.ui)
    implementation(libs.media3.mediasession)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowSizeClass)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.lifecycle.runtime.compose)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(platform(libs.compose.bom))
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // Fancy UI
    implementation(libs.chrisbanes.haze)
    implementation(libs.stoyanvuchev.squircleShape)
    implementation(libs.stoyanvuchev.systemUiBarsTweaker)
    implementation(libs.lottie.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Storage
    implementation(libs.datastore.preferences)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DI
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.dagger.hilt.compiler)

    androidTestImplementation(libs.dagger.hilt.android.testing)

    // Networking
    implementation(libs.retrofit.android)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Serialization
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    // Work Manager
    implementation(libs.workManager.runtime)
    implementation(libs.workManager.runtime.ktx)
    implementation(libs.workManager.multiprocess)

    // Tests
    testImplementation(libs.assertK)
    androidTestImplementation(libs.assertK)
    testImplementation(libs.appCash.turbine)
    androidTestImplementation(libs.appCash.turbine)
    testImplementation(libs.coroutinesTest)
    androidTestImplementation(libs.coroutinesTest)

    // Retrofit + Moshi
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // Coroutines (explicit)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
