package com.goodmood.feature.editor.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.goodmood.core.editor.databinding.FragmentPreviewBinding
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.viewmodel.EditorViewModel
import com.goodmood.platform.log.AppLog
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import io.github.hyuwah.draggableviewlib.DraggableView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

        observeNewText()
    }

    private fun observeNewText() {
        disposable.add(
            editorViewModel.addNewTextRequest
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { addText(it) },
                    { AppLog.e("Add text error. ${it.message}") }
                )
        )
    }

    override fun onDestroy() {
        binding.videoView.player?.stop()
        binding.videoView.player?.release()
        super.onDestroy()
    }

    private fun addSticker(stickerDrawable: Drawable) {
        val imageView = ImageView(context)
        imageView.setImageDrawable(stickerDrawable)
        val draggableView = DraggableView.Builder(imageView).build()
        val param = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        param.addRule(RelativeLayout.CENTER_IN_PARENT)
        binding.container.addView(draggableView.getView(), param)
    }

    private fun addText(text: Text) {
        val textView = TextView(context)
        textView.text = text.text
        val draggableView = DraggableView.Builder(textView).build()
        val param = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        param.addRule(RelativeLayout.CENTER_IN_PARENT)
        binding.container.addView(draggableView.getView(), param)
    }
}