plugins {
    configPluginIsApp(this, false)
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
}


//必须配置main
sourceSets {
    create("main") {
        java.srcDir("src/main/java")
    }
}

//打包源码
val sourcesJar by tasks.registering(Jar::class) {
    //如果没有配置main会报错
    from(sourceSets["main"].allSource)
    archiveClassifier.set("sources")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.rlnb.lib.media"
                artifactId = "library"
                version = "0.0.1"
                from(components["release"])
                artifact(sourcesJar)
            }
        }
    }
}

