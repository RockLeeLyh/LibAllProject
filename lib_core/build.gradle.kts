plugins {
    configPluginIsApp(this,false)
    id("maven-publish")
}

configAndroid(isModule = false, isApp = false)
android {
    // 资源前缀
    resourcePrefix("lcore")
}

importCommonDependencies()
dependencies {
    compileOnly(KtsDependencies.Utils.logger)
}



afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.rlnb.lib.core"
                artifactId = "library"
                version = "0.0.1"
                from(components["release"])
            }
        }
    }
}
