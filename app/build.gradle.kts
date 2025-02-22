plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    //FIREBASE
    //id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.aplicativomovil"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aplicativomovil"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    //FIREBASE
    implementation(platform("com.google.firebase:firebase-bom:33.5.0"))
    implementation ("com.google.firebase:firebase-firestore:24.8.1")
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    //FIREBASE AUTENTICATION
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.firebase.database)
    implementation(libs.play.services.location)

    //FIREBASE CLOUD MESSASING
    implementation(libs.firebase.messaging)
    implementation ("com.google.firebase:firebase-messaging:24.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(kotlin("script-runtime"))
    //FIREBASAE FIREBABASE CLOUD MESSASING(FCM)
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.google.firebase:firebase-messaging:23.0.0")
}