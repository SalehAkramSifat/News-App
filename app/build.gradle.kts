plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.sifat.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sifat.newsapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    // AndroidX Core Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Architectural Components (Lifecycle & ViewModel)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Room Database
    implementation(libs.androidx.room.runtime.v252)
    ksp(libs.androidx.room.compiler.v252)
    implementation(libs.androidx.room.ktx.v252)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core.v173)
    implementation(libs.kotlinx.coroutines.android.v173)

    // Retrofit (Networking)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor.v4110)

    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx.v274)
    implementation(libs.androidx.navigation.ui.ktx.v274)

    // Glide (Image Loading)
    implementation(libs.glide.v4151)
    ksp(libs.compiler.v4151)

    // Optional - Dependency Injection (Dagger/Hilt)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
