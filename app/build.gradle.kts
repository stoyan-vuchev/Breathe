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

    buildTypes {

        release {

            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")

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

    // Puzzle Module

    implementation(project(":puzzle"))

    // GMS Dependencies

    implementation(libs.gms.location)

    // Core Dependencies

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Media3 Dependencies

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.dash)
    implementation(libs.media3.ui)
    implementation(libs.media3.mediasession)

    // Jetpack Compose Dependencies

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

    // Fancy UI Stuff Dependencies :d

    implementation(libs.chrisbanes.haze)
    implementation(libs.stoyanvuchev.squircleShape)
    implementation(libs.stoyanvuchev.systemUiBarsTweaker)
    implementation(libs.lottie.compose)

    // Coroutines Dependencies

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Storage Dependencies

    implementation(libs.datastore.preferences)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Dependency Injection Dependencies

    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)

    ksp(libs.androidx.hilt.compiler)
    ksp(libs.dagger.hilt.compiler)

    androidTestImplementation(libs.dagger.hilt.android.testing)

    // Networking Dependencies

    implementation(libs.retrofit.android)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Serialization Dependencies

    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    // Work Manager Dependency

    implementation(libs.workManager.runtime)
    implementation(libs.workManager.runtime.ktx)
    implementation(libs.workManager.multiprocess)

    // Testing Dependencies

    testImplementation(libs.assertK)
    androidTestImplementation(libs.assertK)

    testImplementation(libs.appCash.turbine)
    androidTestImplementation(libs.appCash.turbine)

    testImplementation(libs.coroutinesTest)
    androidTestImplementation(libs.coroutinesTest)

}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}