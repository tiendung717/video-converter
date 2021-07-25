package com.alticode.feature.video.converter

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.alticode.core.extractor.AltiMediaCodec
import com.alticode.feature.video.converter.databinding.FragmentVideoConverterBinding
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.framework.ui.components.SingleChoiceView
import com.alticode.platform.log.AppLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoConverterFragment : BaseFragment<FragmentVideoConverterBinding>(R.layout.fragment_video_converter) {

    private val args: VideoConverterFragmentArgs by navArgs()

    @Inject
    lateinit var altiMediaCodec: AltiMediaCodec

    private val videoEncoders by lazy { VideoEncoder.get() }
    private val audioEncoders by lazy { AudioEncoder.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // format
        binding.format.setData(
            title = "Format",
            options = videoEncoders
                .flatMap { it.fileTypes }
                .distinctBy { it.name }
                .map { SingleChoiceView.Option(it.name, it) }
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
            options = videoEncoders.map { SingleChoiceView.Option(it.name, it) }
        )

        // audio codec
        binding.audioCodec.setData(
            title = "Audio codec",
            options = audioEncoders.map { SingleChoiceView.Option(it.name, it) }
        )

        binding.btnConvert.setOnClickListener { convert() }


    }

    fun convert() {
        EncoderManager.getVideoEncoders().forEach {
            AppLog.i("Video encoder: ${it.name}")
            it.supportedTypes.forEach { AppLog.i("Mime: $it") }
        }

        EncoderManager.getAudioEncoders().forEach {
            AppLog.i("Audio encoder: ${it.name}")
            it.supportedTypes.forEach { AppLog.i("Mime: $it") }
        }
    }
}