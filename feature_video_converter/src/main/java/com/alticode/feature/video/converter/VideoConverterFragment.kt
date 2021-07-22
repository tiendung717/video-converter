package com.alticode.feature.video.converter

import android.os.Bundle
import android.view.View
import com.alticode.feature.video.converter.databinding.FragmentVideoConverterBinding
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.framework.ui.components.SingleChoiceView

class VideoConverterFragment : BaseFragment<FragmentVideoConverterBinding>(R.layout.fragment_video_converter) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // format
        binding.format.setData(
            title = "Format",
            options = VideoFormat.get().map { SingleChoiceView.Option(it.name, it) }
        )

        // resolution
        binding.resolution.setData(
            title = "Resolution",
            options = VideoResolution.get().map { SingleChoiceView.Option(it.name, it) }
        )

        // frame rate
        binding.frameRate.setData(
            title = "Frame rate",
            options = FrameRate.get().map { SingleChoiceView.Option(it.name, it) }
        )

        // video codec
        binding.videoCodec.setData(
            title = "Video codec",
            options = VideoCodec.get().map { SingleChoiceView.Option(it.name, it) }
        )

        // audio codec
        binding.audioCodec.setData(
            title = "Audio codec",
            options = AudioCodec.get().map { SingleChoiceView.Option(it.name, it) }
        )

        binding.btnConvert.setOnClickListener { convert() }
    }

    fun convert() {
        val format = binding.format.getCurrentValue() as? VideoFormat
    }
}