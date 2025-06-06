import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private const val JDK_VERSION = 17
private val JVM_TARGET = JvmTarget.JVM_11
val JAVA_VERSION = JavaVersion.VERSION_11


internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension,
) = extension.apply {
    jvmToolchain(JDK_VERSION)

    androidTarget {
        compilerOptions {
            jvmTarget.set(JVM_TARGET)
        }
    }

    jvm("desktop")

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()


    sourceSets.apply {
        commonMain {
            dependencies {
                api(libs.findLibrary("kotlinx-coroutines-core").get())
                api(libs.findLibrary("kotlinx-datetime").get())
                api(libs.findLibrary("kotlinx-serialization-json").get())
            }
        }
    }
}