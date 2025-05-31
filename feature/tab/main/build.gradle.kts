plugins {
    alias(libs.plugins.local.kotlin.multiplatform)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common)

            implementation(projects.feature.tab.chat)
            implementation(projects.feature.tab.settings)


            implementation(libs.bundles.decompose)
            implementation(libs.bundles.mvi)
            implementation(libs.bundles.reaktive)
        }
    }
}