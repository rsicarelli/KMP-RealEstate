import Application.Android

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
    id(Plugins.secretsGradle)
    kotlin(Plugins.android)
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.id
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = Android.instrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(Accompanist.animations)
    implementation(Accompanist.insets)
    implementation(Accompanist.pager)
    implementation(Accompanist.pagerIndicators)
    implementation(Accompanist.systemUiController)

    implementation(AndroidX.androidXCore)
    implementation(AndroidX.androidXAppCompat)
    implementation(AndroidX.androidXKTX)
    runtimeOnly(AndroidX.lifecycleRuntimeKtx)

    implementation(Coil.coil)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)
    implementation(Compose.constraintLayout)
    implementation(Compose.utils)

    implementation(Dependencies.multiplatformSettings)
    implementation(Dependencies.touchImageView)
    implementation(Dependencies.timber)

    implementation(Google.material)
    implementation(Google.maps)
    implementation(Google.mapsKtx)
    implementation(Google.mapsUtilsKtx)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(Kotlin.coroutines)

    androidTestImplementation(ComposeTest.uiTestJunit4)
    debugImplementation(ComposeTest.uiTestManifest)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}