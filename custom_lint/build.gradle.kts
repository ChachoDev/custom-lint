plugins {
    id("java-library")
    id("com.android.lint")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

lint {
    htmlReport = true
    htmlOutput = file("lint-report.html") // Archivo de reporte HTML
    textReport = true // También generará un reporte en texto
    checkReleaseBuilds = true // Verifica las compilaciones de release
    absolutePaths = false // Usa rutas relativas en los reportes
    ignoreTestSources = true // Incluye los test en los reportes
    abortOnError = true // Detiene el proceso si hay errores
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.lint.api)
    compileOnly(libs.lint.cli)
    compileOnly(libs.lint.checks)
    compileOnly(libs.lint.tests)
}

tasks.jar {
    manifest {
        attributes(
            "Lint-Registry-v2" to "com.example.custom_lint.CleanArchitectureIssueRegistry",
            "Lint-Vendor" to "The Roustic Chicken" // Reemplaza con el nombre de tu empresa
        )
    }
}

