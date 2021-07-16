package com.goodmood.feature.editor.repository.model

import java.io.Serializable


class Text(
    toolId: Long,
    var text: String,
    var fontPath: String,
    var fontSize: Int,
    var posX: Int,
    var posY: Int
) : Tool(toolId), Serializable {

    override fun hashCode(): Int {
        return text.hashCode()
    }
}