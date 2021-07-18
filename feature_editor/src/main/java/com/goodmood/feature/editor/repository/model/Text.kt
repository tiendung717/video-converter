package com.goodmood.feature.editor.repository.model

import java.io.Serializable


class Text(
    toolId: Long,
    var text: String,
    var fontPath: String,
    var fontSize: Int,
    var fontColor: String,
    var xPercent: Float,
    var yPercent: Float
) : Tool(toolId), Serializable