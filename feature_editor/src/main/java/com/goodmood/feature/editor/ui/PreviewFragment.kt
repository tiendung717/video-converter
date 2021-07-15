package com.goodmood.feature.editor.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goodmood.core.editor.databinding.FragmentPreviewBinding
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.feature.editor.viewmodel.EditorViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

internal class PreviewFragment : BaseFragment() {

    private lateinit var binding: FragmentPreviewBinding
    private val editorViewModel by activityViewModels<EditorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.videoView.player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            val mediaItem = MediaItem.fromUri(editorViewModel.inputVideoUri)
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
        }
    }
}