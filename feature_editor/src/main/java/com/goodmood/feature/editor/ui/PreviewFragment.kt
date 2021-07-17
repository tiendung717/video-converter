package com.goodmood.feature.editor.ui

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentPreviewBinding
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.feature.editor.repository.model.Sticker
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.viewmodel.EditorViewModel
import com.goodmood.platform.log.AppLog
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import io.github.hyuwah.draggableviewlib.DraggableView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

internal class PreviewFragment : BaseFragment() {

    private lateinit var binding: FragmentPreviewBinding
    private val editorViewModel by activityViewModels<EditorViewModel>()
    private val childViewMap = mutableMapOf<Long, View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        }

        binding.videoView.player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            val mediaItem = MediaItem.fromUri(editorViewModel.inputVideoUri)
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
        }

        // observe if any text or sticker is added/updated to the video
        observeTextUpdated()
        observeStickerUpdated()

        // observer if text or sticker is removed
        observeToolRemoved()
    }

    override fun onDestroy() {
        binding.videoView.player?.stop()
        binding.videoView.player?.release()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_editor, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_export) export()
        return true
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

    private fun observeStickerUpdated() {
        disposable.add(
            editorViewModel.observeStickerUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { addSticker(it) },
                    { AppLog.e("Observe new sticker added failed. ${it.message}") })
        )
    }

    private fun observeToolRemoved() {
        disposable.add(
            editorViewModel.observeToolDeleted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { binding.container.removeView(childViewMap[it.toolId]) },
                    { AppLog.e("Observe sticker deleted failed. ${it.message}") })
        )
    }

    private fun addSticker(sticker: Sticker) {
        val view = childViewMap[sticker.toolId]
        if (view == null) {
            val imageView = ImageView(context)
            imageView.setImageURI(Uri.fromFile(File(sticker.path)))
            val draggableView = DraggableView.Builder(imageView).build()
            val param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            param.addRule(RelativeLayout.CENTER_IN_PARENT)
            binding.container.addView(draggableView.getView(), param)

            childViewMap[sticker.toolId] = draggableView.getView()
        }
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

    private fun updateToolsLocation() {
        childViewMap.forEach {
            val view = it.value
            val x = view.x
            val y = view.y
            val videoW = binding.videoView.width
            val videoH = binding.videoView.height

            val tool = editorViewModel.getTool(it.key)
            tool?.let {
                when (tool) {
                    is Text -> {
                        tool.xPercent = x / videoW
                        tool.yPercent = y / videoH
                    }
                    is Sticker -> {
                        tool.xPercent = x / videoW
                        tool.yPercent = y / videoH
                    }
                }
                editorViewModel.updateTool(tool)
            }
        }
    }

    private fun export() {
        if (activity is AppCompatActivity) {
            updateToolsLocation()
            editorViewModel.export(activity as AppCompatActivity)
        }
    }
}