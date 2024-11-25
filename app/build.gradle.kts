plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
}

android {
    namespace = "com.example.githubtest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.githubtest"
        minSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes +="META-INF/INDEX.LIST" // Add this line
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    // navigation
    implementation(libs.androidx.compose.navigation)

    // log
//    implementation(libs.slf4j.api)
//    implementation(libs.logback.android)
//    implementation(libs.timber)
//    implementation(libs.logback.classic)
    implementation("io.github.aakira:napier:1.4.1")

    // meterial icons
    implementation (libs.androidx.material.icons.extended)

    // viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")


//    implementation(libs.ktor.client.core)
//    implementation(libs.ktor.client.cio)
//    implementation(libs.ktor.client.logging)
    implementation(libs.bundles.ktor)

    //image
    implementation("io.coil-kt:coil-compose:2.7.0")
}