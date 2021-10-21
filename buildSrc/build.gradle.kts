plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
    google()
    jcenter()
}

gradlePlugin {
    plugins {
        register("kts-jvm-target-plugin") {
            id = "kts-jvm-target-plugin"
            implementationClass = "plugins.KtsJvmTargetPlugin"
        }
    }
}

dependencies {
    // Depend on the android gradle plugin, since we want to access it in our plugin
    implementation("com.android.tools.build:gradle:4.0.0")
//    implementation(KtsDependencies.gradleTool)

    // Depend on the kotlin plugin, since we want to access it in our plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")

    // Depend on the default Gradle API since we want to build a custom plugin
//    implementation(gradleApi())
//    implementation(localGroovy())
}