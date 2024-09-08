package com.example.custom_lint

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UClass
import org.jetbrains.uast.getContainingUFile

class PresentationDependencyDetector : Detector(), SourceCodeScanner {

    override fun applicableSuperClasses(): List<String>? = listOf("java.lang.Object")
    override fun visitClass(context: JavaContext, declaration: UClass) {
        val uFile = declaration.getContainingUFile()
        val packageName = uFile?.packageName ?: return
        val regexPresentation = Regex("""^com\.example(?:\.customlintexample)?\.presentation(?:\.\w+)*$""")

        if (regexPresentation.matches(packageName)) {
            val imports = context.uastFile?.imports ?: return

            for (importStatement in imports) {
                val import = importStatement.asSourceString()
                val regexData = Regex("""^import\s+com\.example(?:\.\w+)?\.data(?:\.\w+)*""")

                if (regexData.matches(import)) {
                    context.report(
                        ISSUE, declaration,
                        context.getLocation(importStatement),
                        "El paquete presentation no debe depender de data."
                    )
                }
            }
        }
    }

    companion object {
        val ISSUE: Issue = Issue.create(
            id = "PresentationDependency",
            briefDescription = "Dependencia prohibida en Presentation",
            explanation = "El paquete presentation no debe depender de data.",
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.ERROR,
            implementation = Implementation(
                PresentationDependencyDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}