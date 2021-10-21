import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

/** 庫版本管理類 */
object KtsDependencies {
    /* [https://developer.android.com/studio/releases/gradle-plugin] */
    private const val GRADLE_TOOL_VERSION = "4.0.0"
    const val gradleTool = "com.android.tools.build:gradle:${GRADLE_TOOL_VERSION}"

    /* [https://developer.android.com/jetpack/androidx/versions] */
    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val material = "com.google.android.material:material:1.3.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val cardview = "androidx.cardview:cardview:1.0.0"

        private const val pagingVersion = "3.0.1"
        const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${pagingVersion}"
    }

    object Kotlin {
        const val jvmTarget = "1.8"

        private const val KOTLIN_VERSION = "1.4.31"

        //        private const val KOTLIN_VERSION = "1.5.0"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KOTLIN_VERSION}"
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${KOTLIN_VERSION}"
        const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:${KOTLIN_VERSION}"
        const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KOTLIN_VERSION}"
        const val kotlinNoarg = "org.jetbrains.kotlin:kotlin-noarg:${KOTLIN_VERSION}"
        const val kotlinAllopen = "org.jetbrains.kotlin:kotlin-allopen:${KOTLIN_VERSION}"
    }

    object Test {

        const val androidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"

        const val junit = "junit:junit:4.12"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }

    object Media {
        /* 图片加载库 [https://github.com/bumptech/glide] */
        private const val glideVersion = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:${glideVersion}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${glideVersion}"
        /* 圖片裁剪 [https://github.com/Yalantis/uCrop] */
        const val ucrop = "com.github.yalantis:ucrop:2.2.7"
    }

    object Utils {
        /* [https://github.com/orhanobut/logger] */
        const val logger = "com.orhanobut:logger:2.2.0"
    }

}



fun Project.importCommonDependencies(){
    val implementation by configurations
    val testImplementation by configurations
    val androidTestImplementation by configurations
//    val kapt by configurations
    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.arr"))))

        implementation(KtsDependencies.Kotlin.kotlinStdlib)
        implementation(KtsDependencies.Kotlin.kotlinTestJunit)
        implementation(KtsDependencies.AndroidX.coreKtx)
        implementation(KtsDependencies.AndroidX.appcompat)
        implementation(KtsDependencies.AndroidX.material)
        implementation(KtsDependencies.AndroidX.constraintlayout)
        implementation(KtsDependencies.AndroidX.recyclerview)
        implementation(KtsDependencies.AndroidX.cardview)
        implementation(KtsDependencies.AndroidX.pagingRuntime)


        testImplementation(KtsDependencies.Test.junit)
        androidTestImplementation(KtsDependencies.Test.extJunit)
        androidTestImplementation(KtsDependencies.Test.espressoCore)
    }
}