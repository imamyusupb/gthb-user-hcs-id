@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinKapt)

}

android {
    namespace = "com.hcs.gitmeshow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hcs.gitmeshow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

    dependencies {
        implementation(libs.core.ktx)
        implementation(libs.lifecycle.viewmodel)
        implementation(libs.lifecycle.livedata)
        implementation(libs.coroutines.core)
        implementation(libs.coroutines.android)

        // Network
        implementation(libs.retrofit)
        implementation(libs.retrofit.moshi)
        implementation(libs.okhttp.logging)
        implementation(libs.moshi)
        implementation(libs.moshi.kotlin)

        // Room
        implementation(libs.room.runtime)
        implementation(libs.room.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        kapt(libs.room.compiler)

        // Hilt
        implementation(libs.hilt.android)
        kapt(libs.hilt.compiler)
        // WorkManager
        implementation(libs.work.runtime)

        // UI
        implementation(libs.glide)
        implementation(libs.lottie)
        implementation(libs.constraintlayout)

        // Debugging
        debugImplementation(libs.okhttp.chucker)
        releaseImplementation(libs.okhttp.chuckerNoop)
        // Test
        testImplementation(libs.junit)
        androidTestImplementation(libs.espresso.core)
    }