package com.goodmood.feature.editor.viewmodel

sealed class ExportResult {

    data class Success(val output: String): ExportResult()
    data class Failed(val reason: String): ExportResult()
    data class InProgress(val progress: String): ExportResult()
}