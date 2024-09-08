plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler) apply true

    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)

}

android {
    namespace = "com.example.customlintexample"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.customlintexample"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
    lint {
        htmlReport = true
        htmlOutput = file("lint-report.html")
        textReport = true
        checkReleaseBuilds = true
        absolutePaths = false
        ignoreTestSources = false
        abortOnError = true
    }
}

dependencies {
    lintChecks(project(":custom_lint"))

    //region Hilt (Inyección de dependencias)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //endregion

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

//// Define la tarea "lintBeforeBuild"
//tasks.register("lintBeforeBuild") {
//    doLast {
//        try {
//            // Ejecuta el comando ./gradlew lint en el directorio del proyecto
//            exec {
//                workingDir = project.rootDir
//                commandLine("./gradlew", "lint")
//                errorOutput = System.err //Redigimos el flujo de error a la salida standard de errores
//                standardOutput = System.out //Redigimos el flujo de error a la salida standard
//            }
//        } catch (e: Exception) {
//            // Imprime el error y falla la tarea
//            throw GradleException("La tarea 'lint' falló. Deteniendo la construcción. Detalles: ${e.message}")
//        }
//    }
//}
//
//// Asegúrate de que "lintBeforeBuild" se ejecute antes de `assembleDebug` y `installDebug`
//tasks.whenTaskAdded {
//    val runTasklintBeforeBuild =
//        name == "assembleDebug" || name == "assembleRelease" || name == "installDebug"
//                || name == "installRelease"
//    if (runTasklintBeforeBuild) {
//        dependsOn("lintBeforeBuild")
//    }
//}