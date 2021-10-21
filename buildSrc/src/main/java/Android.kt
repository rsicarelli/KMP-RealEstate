object Application {
    const val group = "com.homehunt"
    const val version = "0.1"
    const val srcSet = "main"

    object Android {
        const val id = "com.rsicarelli.homehunt"
        const val minSdk = 21
        const val targetSdk = 31
        const val compileSdk = targetSdk
        const val versionCode = 1
        const val versionName = "1.0"
        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val manifestPath = "src/androidMain/AndroidManifest.xml"
    }

    @Suppress("ClassName") //Ios not, thanks
    object iOS {
        const val ios = "ios"
        const val iPhoneOS = "iphoneos"
        const val arm = "arm"

        object CocoaPods {
            const val summary = "Home Hunt shared module"
            const val homepage = "www.homehunt.com"
            const val deploymentTarget = "14.1"
            const val podFilePath = "../iosApp/Podfile"
            const val frameworkName = "shared"
        }
    }
}