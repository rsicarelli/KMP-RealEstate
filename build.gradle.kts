// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroid)
        classpath(Build.googleServicesPlugin)
        classpath(Build.googleSecretesPlugin)
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}