package com.goodmood.feature.editor.repository.model


class Text(
    toolId: Long,
    val text: String,
    val fontPath: String,
    val fontSize: Int,
    val posX: Int,
    val posY: Int
) : Tool(toolId)