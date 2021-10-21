plugins {
    configPluginIsApp(this,false)
   id("maven-publish")
}

configAndroid(isModule = false, isApp = false)
android {
    // 资源前缀
    resourcePrefix("lmedia")
}

importCommonDependencies()
dependencies {

    compileOnly(KtsDependencies.Media.glide)
    compileOnly(KtsDependencies.Utils.logger)
    kapt(KtsDependencies.Media.glideCompiler)


    compileOnly(project(":lib_core"))
}



afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.rlnb.lib.media"
                artifactId = "library"
                version = "0.0.1"
                from(components["release"])
            }
        }
    }
}
