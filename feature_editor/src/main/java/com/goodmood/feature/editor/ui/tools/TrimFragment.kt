package com.goodmood.feature.editor.ui.tools

import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentTrimBinding
import com.goodmood.feature.editor.repository.model.Trim
import com.goodmood.feature.editor.ui.ToolFragment
import com.goodmood.feature.editor.utils.toTime

internal class TrimFragment : ToolFragment() {

    private lateinit var binding: FragmentTrimBinding
    private val metaRetriever = MediaMetadataRetriever()

    override fun getTitle() = R.string.edit_trim
    override fun getIcon() = R.drawable.ic_trim

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrimBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        metaRetriever.setDataSource(editorViewModel.inputVideoPath)
        val durationStr =
            metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val duration = durationStr?.toLong() ?: 0

        binding.trimSlider.apply {
            valueFrom = 0f
            valueTo = duration.toFloat()
            setLabelFormatter { it.toLong().toTime() }
            values = listOf(0f, duration.toFloat())

            updateTrimDuration(values)
            addOnChangeListener { slider, _, _ ->
                updateTrimDuration(slider.values)

                val trim = Trim(values[0].toLong(), values[1].toLong())
                editorViewModel.updateTool(trim)
            }
        }
    }

    override fun onDestroy() {
        metaRetriever.release()
        super.onDestroy()
    }

    private fun updateTrimDuration(values: List<Float>) {
        binding.tvTrimStart.text = values[0].toLong().toTime()
        binding.tvTrimEnd.text = values[1].toLong().toTime()
    }
}