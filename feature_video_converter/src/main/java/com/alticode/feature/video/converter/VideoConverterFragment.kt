package com.alticode.feature.video.converter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alticode.core.extractor.AltiMediaCodec
import com.alticode.core.extractor.model.MediaEncoderParam
import com.alticode.feature.video.converter.databinding.FragmentVideoConverterBinding
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.framework.ui.components.SingleChoiceView
import com.alticode.platform.utils.FileUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class VideoConverterFragment :
    BaseFragment<FragmentVideoConverterBinding>(R.layout.fragment_video_converter) {

    private val args: VideoConverterFragmentArgs by navArgs()

    @Inject
    lateinit var altiMediaCodec: AltiMediaCodec

    private val videoEncoders by lazy { VideoEncoder.get() }
    private val audioEncoders by lazy { AudioEncoder.get() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // format
        binding.format.apply {
            title = "Format"
            optionList = videoEncoders
                .flatMap { it.fileTypes }
                .distinctBy { it.name }
                .map { SingleChoiceView.Option(it.name, it) }
            optionChangedListener = {
                val currentFileType = it.value as FileType
                binding.videoCodec.optionList = videoEncoders
                    .filter { videoEncoder -> videoEncoder.isSupport(currentFileType.name) }
                    .map { videoEncoder ->
                        SingleChoiceView.Option(videoEncoder.name, videoEncoder)
                    }
            }
        }

        // audio codec
        binding.audioCodec.apply {
            title = "Audio codec"
            optionList = audioEncoders.map { SingleChoiceView.Option(it.name, it) }
        }

        // video codec
        binding.videoCodec.apply {
            val currentFileType = binding.format.selectedOption as FileType
            title = "Video codec"
            optionList = videoEncoders
                .filter { encoder -> encoder.isSupport(currentFileType.name) }
                .map {
                    SingleChoiceView.Option(it.name, it)
                }
            optionChangedListener = {
                onVideoEncoderChanged()
            }
        }

        // Resolution
        binding.resolution.apply {
            title = "Resolution"
            optionList = VideoResolution.get()
                .filter { filterResolution(it) }
                .map { SingleChoiceView.Option(it.name, it) }
        }

        // Frame rate
        binding.frameRate.apply {
            title = "Frame rate"
            optionList = FrameRate.get()
                .filter { filterFrameRate(it) }
                .map { SingleChoiceView.Option(it.name, it) }
        }

        binding.sliderVideoBitrate.apply {
            val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
            valueFrom = videoEncoder.getBitrateSupported().lower.toFloat()
            valueTo = videoEncoder.getBitrateSupported().upper.toFloat()
            value = valueTo
        }

        binding.btnConvert.setOnClickListener { convert() }
    }

    private fun onVideoEncoderChanged() {
        binding.frameRate.optionList = FrameRate.get()
            .filter { filterFrameRate(it) }
            .map { SingleChoiceView.Option(it.name, it) }

        binding.resolution.optionList = VideoResolution.get()
            .filter { filterResolution(it) }
            .map { SingleChoiceView.Option(it.name, it) }

        binding.sliderVideoBitrate.apply {
            val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
            valueFrom = videoEncoder.getBitrateSupported().lower.toFloat()
            valueTo = videoEncoder.getBitrateSupported().upper.toFloat()
            value = valueTo
        }
    }

    private fun filterFrameRate(frameRate: FrameRate): Boolean {
        val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
        return frameRate.value <= videoEncoder.getFrameSupported().upper
    }

    private fun filterResolution(resolution: VideoResolution): Boolean {
        val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
        return resolution.value <= videoEncoder.getHeightSupported().upper
    }

    private fun getEncoderParam(): MediaEncoderParam {
        val format = binding.format.selectedOption as FileType
        val resolution = binding.resolution.selectedOption as VideoResolution
        val frameRate = binding.frameRate.selectedOption as FrameRate
        val videoBitrate = binding.sliderVideoBitrate.value.toInt()
        val videoEncoder = binding.videoCodec.selectedOption as VideoEncoder
        val audioEncoder = binding.audioCodec.selectedOption as AudioEncoder
        val outputPath = FileUtils.getOutputPath(File(args.path).name, format.name.lowercase())

        val mediaInfo = altiMediaCodec.extractMedia(args.path)

        return MediaEncoderParam(
            inputPath = args.path,
            outputPath = outputPath,
            videoMimeType = videoEncoder.mimeType,
            videoEncoderProfile = videoEncoder.profile,
            width = resolution.getWidth(mediaInfo.width, mediaInfo.height),
            height = resolution.value,
            frameRate = frameRate.value,
            videoBitRate = videoBitrate,
            audioMimeType = audioEncoder.mimeType,
            audioChannelCount = mediaInfo.audioChannelCount,
            audioSampleRate = mediaInfo.audioSampleRate,
            audioBitRate = 128_000
        )
    }

    private fun convert() {
        lifecycleScope.launch {
            val encoderParam = getEncoderParam()
            altiMediaCodec.encodeVideo(encoderParam)
        }
    }
}