package de.rohitmisra.muenchen.ausweisstatus.model

data class DocumentInfo(
    val tag: String,
    val holder: String,
    val documentId: String,
    val documentType: String
)