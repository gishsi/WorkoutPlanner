import com.android.build.gradle.internal.scope.ProjectInfo.Companion.getBaseName

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "uk.ac.aber.dcs.cs31620.jud28.workoutplanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.ac.aber.dcs.cs31620.jud28.workoutplanner"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1-dev"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    kotlin {
        jvmToolchain(17)
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            enableAndroidTestCoverage = true
        }
    }

    // https://developer.android.com/build/build-variants#filter-variants
    flavorDimensions += "testing"
    productFlavors {
        create("in_memory_database") {
            dimension = "testing"
        }

        create("persistent_database") {
            dimension = "testing"
        }
    }

    androidComponents {
        beforeVariants { variantBuilder ->
            if(variantBuilder.buildType == "release" && variantBuilder.flavorName?.contains("in_memory_database") == true) {
                variantBuilder.enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material3
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    // Navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.5")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    //Room
    implementation("androidx.room:room-common:2.6.0")
    implementation("androidx.lifecycle:lifecycle-common:2.6.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.compose.foundation:foundation:1.6.0-beta03")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.6.0-beta03")
    implementation("androidx.compose.ui:ui-tooling:1.6.0-beta03")
    implementation("androidx.compose.runtime:runtime:1.6.0-beta03")
    implementation("androidx.compose.ui:ui-graphics:1.6.0-beta03")
    implementation("androidx.compose.ui:ui-test-junit4:1.6.0-beta03")
    implementation("androidx.navigation:navigation-testing:2.7.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.0-beta03")
    ksp("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")


    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0-beta03")
}