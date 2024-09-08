package com.example.custom_lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

class CleanArchitectureIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        DomainDependencyDetector.ISSUE,
        DataDependencyDetector.ISSUE,
        PresentationDependencyDetector.ISSUE,
        //LayerDependencyDetector.ISSUE_IMPORT_DETECTOR
    )
    override val api = CURRENT_API

    // Agrega el atributo del proveedor aquí
    override val vendor: Vendor = Vendor("The Country Chicken")
}