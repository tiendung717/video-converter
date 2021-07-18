package com.goodmood.feature.editor.repository.model

class Sticker(toolId: Long, val path: String, var scale: Float, var xPercent: Float, var yPercent: Float) : Tool(toolId)