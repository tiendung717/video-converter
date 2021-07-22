package com.alticode.feature.video.converter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alticode.core.extractor.MediaDecoder
import com.alticode.feature.video.converter.databinding.FragmentVideoConverterBinding
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.framework.ui.components.SingleChoiceView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideoConverterFragment : BaseFragment<FragmentVideoConverterBinding>(R.layout.fragment_video_converter) {

    private val args: VideoConverterFragmentArgs by navArgs()

    @Inject
    lateinit var mediaDecoder: MediaDecoder

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

        lifecycleScope.launch {
            mediaDecoder.extractMedia(args.path)
        }
    }

    fun convert() {
        val format = binding.format.getCurrentValue() as? VideoFormat
    }
}