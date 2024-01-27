
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("id.harlabs.delight.gen")
        }
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.android)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.sqlite)
        }
    }
}

android {
    namespace = "id.harlabs.example.todo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
