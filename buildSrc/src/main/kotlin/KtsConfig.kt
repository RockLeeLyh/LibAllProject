import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.api.JavaVersion

val commonApplicationPlugins = arrayOf("com.android.application", "kotlin-android", "kotlin-kapt")
val commonLibPlugins = arrayOf("com.android.library", "kotlin-android", "kotlin-kapt")

/**
 * 插件配置
 * @param pds
 * @param isApp    模塊化調試時傳入true
 */
fun configPluginIsApp(pds: PluginDependenciesSpec, isApp: Boolean) {
    if (isApp) {
        commonApplicationPlugins.forEach { pds.id(it) }
    } else {
        commonLibPlugins.forEach { pds.id(it) }
    }
    pds.id("kts-jvm-target-plugin")
}

/** 擴展签名配置 */
fun Project.configAndroidSigningConfigs() = this.extensions.getByType<BaseExtension>().run {
//    println("properties is rootDir = ${properties.get("rootDir")}/${Versions.Signing.storeFile}")
    // 签名配置
    signingConfigs {
        register("release") {
            // 签名文件路径
            storeFile = file("${properties["rootDir"]}/${KtsVersions.Signing.storeFile}")
            // 密码
            storePassword = KtsVersions.Signing.storePassword
            // 别名
            keyAlias = KtsVersions.Signing.keyAlias
            // 别名密码
            keyPassword = KtsVersions.Signing.keyPassword
        }
    }
}

/** 擴展打包環境配置 */
fun Project.configAndroidBuildTypes() = this.extensions.getByType<BaseExtension>().run {
    buildTypes {
        getByName("debug") {

            isDebuggable = true
            isJniDebuggable = true
            isMinifyEnabled = false
            // 自动签名打包
            signingConfig = signingConfigs.getByName("release")

            buildConfigField("String", "REST_BASE_URL", "\"http://www.baid.debug\"")
            // [android模拟器访问localhost或127.0.0.1报错](https://blog.csdn.net/aoyunlian9546/article/details/102374805?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_baidulandingword-1&spm=1001.2101.3001.4242)
            buildConfigField("String", "REST_BASE_LOCAL_URL", "\"http://10.0.2.2:8888\"")
        }
        getByName("release") {

            isDebuggable = false
            isMinifyEnabled = false
            // 自动签名打包
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "REST_BASE_URL", "\"http://www.baid.release\"")
            buildConfigField("String", "REST_BASE_LOCAL_URL", "\"http://10.0.2.2:8888\"")
        }
    }
}


/** 擴展Jetpack配置 */
fun Project.configAndroidJetpack() = this.extensions.getByType<BaseExtension>().run {
    buildFeatures.viewBinding = true
    buildFeatures.dataBinding = true
}

/** 擴展编译配置 */
fun Project.configAndroidCompileOptions() =
    this.extensions.getByType<BaseExtension>().run {
        // java8的新特性也是可以正常编译了，但是配置只是针对java代码的编译
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }


/**
 * android 公共配置
 * @param isModule  是否需要模塊化，true - 需要
 */
fun Project.configAndroid(isModule: Boolean,isApp:Boolean =false) = this.extensions.getByType<BaseExtension>().run {

    compileSdkVersion(KtsVersions.compileSdkVersion)
    buildToolsVersion(KtsVersions.buildToolsVersion)

    defaultConfig {

        minSdkVersion(KtsVersions.minSdkVersion)
        targetSdkVersion(KtsVersions.targetSdkVersion)
        versionCode = KtsVersions.versionCode
        versionName = KtsVersions.versionName
        if (isApp) {
            multiDexEnabled = KtsVersions.multiDexEnabled
        }
        testInstrumentationRunner = KtsDependencies.Test.androidJUnitRunner

    }
    // 擴展資源重定向配置
//    configAndroidResourceRedirection()
    // 擴展签名配置
    configAndroidSigningConfigs()
    // 擴展打包環境配置
    configAndroidBuildTypes()
    // 擴展Jetpack配置
    configAndroidJetpack()
    // 擴展编译配置
    configAndroidCompileOptions()

    //模塊化設置
    if (isModule) {
//        configAndroidModule()
    }
}