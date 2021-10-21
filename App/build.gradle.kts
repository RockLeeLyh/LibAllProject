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
    kapt(KtsDependencies.Media.glideCompiler)

    implementation(KtsDependencies.ModuleLib.media)
//    implementation(project(":lib_media"))
    implementation(project(":lib_core"))
}