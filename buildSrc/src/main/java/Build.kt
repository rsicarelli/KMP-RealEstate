object Build {

    private const val androidBuildToolsVersion = "7.1.0-beta01"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"

    private const val googleServicesVersion = "4.3.10"
    const val googleServicesPlugin =
        "com.google.gms:google-services:$googleServicesVersion"

    private const val googleSecretsVersion = "2.0.0"
    const val googleSecretesPlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:$googleSecretsVersion"
}