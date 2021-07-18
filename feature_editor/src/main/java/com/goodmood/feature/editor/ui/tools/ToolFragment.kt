package com.goodmood.feature.editor.ui.tools

import androidx.fragment.app.activityViewModels
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.feature.editor.viewmodel.EditorViewModel

abstract class ToolFragment : BaseFragment() {

    protected val editorViewModel by activityViewModels<EditorViewModel>()

    abstract fun getTitle(): Int
    abstract fun getIcon(): Int
}