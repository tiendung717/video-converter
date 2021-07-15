package com.goodmood.feature.editor.ui.tools

object ToolFragmentManager {

    fun getToolFragments() = listOf(
        TrimFragment(),
        TextFragment(),
        StickerFragment()
    )
}