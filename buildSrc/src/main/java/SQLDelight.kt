object SQLDelight {
    const val sqlDelightVersion = "1.5.2"
    const val runtime = "com.squareup.sqldelight:runtime:${sqlDelightVersion}"
    const val androidDriver = "com.squareup.sqldelight:android-driver:${sqlDelightVersion}"
    const val nativeDriver = "com.squareup.sqldelight:native-driver:${sqlDelightVersion}"

    object Database {
        const val name = "HomeHuntDatabase"
        const val packageName = "com.rsicarelli.homehunt_kmm.datasource.cache"
        const val sourceFolder = "sqldelight"
    }

}