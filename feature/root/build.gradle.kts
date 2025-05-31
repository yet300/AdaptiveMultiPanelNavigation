plugins {
    alias(libs.plugins.local.kotlin.multiplatform)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common)
            implementation(projects.feature.tab.main)

            implementation(libs.bundles.decompose)
            implementation(libs.bundles.reaktive)
        }
    }
}