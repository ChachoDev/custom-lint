package com.example.custom_lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiInvalidElementAccessException
import org.jetbrains.uast.UClass
import org.jetbrains.uast.getContainingUFile

class DomainDependencyDetector : Detector(), SourceCodeScanner {

    override fun applicableSuperClasses(): List<String>? =
        listOf("java.lang.Object") // se aplica a todas las clases que heredan de Object

    override fun visitClass(context: JavaContext, declaration: UClass) {
        // Obtiene el nombre del paquete del archivo actual
        val uFile = declaration.getContainingUFile()
        val packageName = uFile?.packageName ?: return
        // Expresión regular para validar el nombre del paquete
        val regexDomain = Regex("""^com\.example(?:\.customlintexample)?\.domain(?:\.\w+)*$""")

        // Verifica si el nombre del paquete pertenece al dominio
        if (regexDomain.matches(packageName)) {
            // Obtiene las declaraciones de importación del archivo actual
            val imports = context.uastFile?.imports ?: return

            for (importStatement in imports) {
                val import = importStatement.asSourceString()
                // Expresión regular para verificar las importaciones prohibidas
                val regexData = Regex("""^import\s+com\.example(?:\.\w+)?\.data(?:\.\w+)*""")
                val regexPresentation =
                    Regex("""^import\s+com\.example(?:\.\w+)?\.presentation(?:\.\w+)*""")
                // Verifica si alguna importación coincide con las expresiones regulares prohibidas
                if (regexData.matches(import) || regexPresentation.matches(import)) {
                    context.report(
                        ISSUE, declaration,
                        context.getLocation(PsiInvalidElementAccessException.isTrackingInvalidation()),
                        "El paquete domain no debe depender de data o presentation."
                    )
                    // Reporta el error de Lint si se encuentra una dependencia no permitida
                    context.report(
                        ISSUE, declaration,
                        context.getLocation(importStatement),
                        "El paquete domain no debe depender de data o presentation."
                    )
                }
            }
        }
    }

    companion object {
        val ISSUE: Issue = Issue.create(
            id = "ImportDependencyViolation",
            briefDescription = "Import Dependency Violation",
            explanation = "Ensures that imports comply with architectural rules.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 6,
            severity = Severity.ERROR,
            implementation = Implementation(
                DomainDependencyDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}