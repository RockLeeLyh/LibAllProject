plugins {
    configPluginIsApp(this, true)
}

configAndroid(isModule = false, isApp = true)
android {
    defaultConfig {
        applicationId = "com.rlnb.lib.project.app"
    }
}
importCommonDependencies()
dependencies {

    implementation(KtsDependencies.Media.glide)
    implementation(KtsDependencies.Utils.logger)
    annotationProcessor(KtsDependencies.Media.glideCompiler)

    implementation(project(":lib_media"))
}