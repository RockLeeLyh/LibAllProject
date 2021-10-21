package plugins

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * @author RL
 * @date 2021/10/21 14:30
 * @description  kotlin代码编译的时候依然是使用的jdk1.6在编译。要想kotlin也用jdk1.8编译，需要添加kotlin相关的编译配置
 * 在APP的gradle的Android标签下==>
 * kotlinOptions {
 *  jvmTarget = "1.8"
 * }
 */
class KtsJvmTargetPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        val androidExtension = project.extensions.getByName("android")
        if (androidExtension is BaseExtension) {
            androidExtension.apply {
                project.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "1.8"
//                        val fca = mutableListOf("-Xopt-in=kotlin.RequiresOptIn")
//                        fca.addAll(freeCompilerArgs)
//                        freeCompilerArgs = fca
                    }
                }
            }
        }
    }
}