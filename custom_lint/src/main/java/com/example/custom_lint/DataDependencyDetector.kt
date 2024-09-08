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

class DataDependencyDetector : Detector(), SourceCodeScanner {

    override fun applicableSuperClasses(): List<String>? = listOf("java.lang.Object")

    override fun visitClass(context: JavaContext, declaration: UClass) {
        val uFile = declaration.getContainingUFile()
        val packageName = uFile?.packageName ?: return
        val regexData = Regex("""^com\.example(?:\.customlintexample)?\.data(?:\.\w+)*$""")

        if (regexData.matches(packageName)) {
            val imports = context.uastFile?.imports ?: return

            for (importStatement in imports) {
                val import = importStatement.asSourceString()
                val regexPresentation =
                    Regex("""^import\s+com\.example(?:\.\w+)?\.presentation(?:\.\w+)*""")

                if (regexPresentation.matches(import)) {
                    context.report(
                        ISSUE, declaration,
                        context.getLocation(importStatement),
                        "El paquete data no debe depender de presentation."
                    )
                }
            }
        }
    }

    companion object {
        val ISSUE: Issue = Issue.create(
            id = "DataDependency",
            briefDescription = "Dependencia prohibida en Data",
            explanation = "El paquete data no debe depender de presentation.",
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.ERROR,
            implementation = Implementation(
                DataDependencyDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}