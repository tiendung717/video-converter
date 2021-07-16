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
    private val childViewMap = mutableMapOf<Long, View>()

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

        observeTextUpdated()
        observeTextDeleted()
    }

    private fun observeTextUpdated() {
        disposable.add(
            editorViewModel.observeTextUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { addOrUpdateText(it) },
                    { AppLog.e("Add text error. ${it.message}") }
                )
        )
    }

    private fun observeTextDeleted() {
        disposable.add(
            editorViewModel.observeTextDeleted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { binding.container.removeView(childViewMap[it.toolId]) },
                    { AppLog.e("Add text error. ${it.message}") }
                )
        )
    }

    override fun onDestroy() {
        binding.videoView.player?.stop()
        binding.videoView.player?.release()
        super.onDestroy()
    }

    private fun addOrUpdateSticker(stickerDrawable: Drawable) {
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

    private fun addOrUpdateText(text: Text) {
        val view = childViewMap[text.toolId]
        if (view != null) {
            if (view is TextView) {
                view.text = text.text
                view.textSize = text.fontSize.toFloat()
            }
        } else {
            val newTextView = TextView(context)
            newTextView.text = text.text
            newTextView.textSize = text.fontSize.toFloat()
            val draggableView = DraggableView.Builder(newTextView).build()
            val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            param.addRule(RelativeLayout.CENTER_IN_PARENT)
            binding.container.addView(draggableView.getView(), param)

            childViewMap[text.toolId] = draggableView.getView()
        }
    }
}