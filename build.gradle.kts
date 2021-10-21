// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(KtsDependencies.gradleTool)
        classpath(KtsDependencies.Kotlin.kotlinGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
//        maven("https://dl.bintray.com/tencent-soter/maven/")
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}