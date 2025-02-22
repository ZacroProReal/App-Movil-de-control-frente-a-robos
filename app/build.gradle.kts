plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
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

        // Especificar el runner de pruebas para instrumentación
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

    // Opcional: Habilitar el orquestador de pruebas en Test Lab
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

dependencies {

    // Dependencias principales
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")

    // Firebase BoM (Usar una sola versión)
    implementation(platform("com.google.firebase:firebase-bom:33.5.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-messaging")

    // Google Play Services - Ubicación
    implementation(libs.play.services.location)

    // HTTP Requests (OkHttp)
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    // JUnit 5 para pruebas unitarias
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    // Excluir Hamcrest si es necesario
    configurations.all {
        exclude(group = "org.hamcrest", module = "hamcrest-core")
    }

    // Kotlin Script Runtime (Corrección)
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.9.22")

    // Dependencias para pruebas instrumentadas (Android Test)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Opcional: Orquestador de pruebas para Firebase Test Lab
    androidTestUtil("androidx.test:orchestrator:1.4.2")
}
