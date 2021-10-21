plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
    id(Plugins.googleServices)
    id(Plugins.secretsGradle)
    kotlin(Plugins.android)
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        applicationId = Application.id
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName

        testInstrumentationRunner = Application.instrumentationRunner
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

    androidTestImplementation(ComposeTest.uiTestJunit4)
    debugImplementation(ComposeTest.uiTestManifest)

    implementation(Dependencies.coroutines)
    implementation(Dependencies.touchImageView)
    implementation(Dependencies.timber)

    implementation(platform(Firebase.bom))
    implementation((Firebase.firestore))
    implementation(Firebase.auth)
    implementation(Firebase.playServicesAuth)

    implementation(Google.material)
    implementation(Google.maps)
    implementation(Google.mapsKtx)
    implementation(Google.mapsUtilsKtx)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}