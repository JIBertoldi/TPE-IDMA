plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.parcelizeKotlinAndroid)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0


android {
    namespace = "ar.edu.unicen.tpe"
    compileSdk = 34

    defaultConfig {
        applicationId = "ar.edu.unicen.tpe"
        minSdk = 26
        targetSdk = 34
        //versionCode = 1
        //versionName = "1.0"
        versionCode = versionMajor * 100 + versionMinor * 10 + versionPatch
        versionName = "$versionMajor.$versionMinor.$versionPatch"

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

    buildFeatures {
        viewBinding = true;
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activityKtx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.glide)

    implementation(libs.hilt)
    implementation(libs.androidx.swiperefreshlayout)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}