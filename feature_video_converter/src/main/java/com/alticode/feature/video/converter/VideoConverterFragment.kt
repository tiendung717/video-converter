package com.alticode.feature.video.converter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alticode.core.extractor.AltiMediaCodec
import com.alticode.core.extractor.model.MediaEncoderParam
import com.alticode.core.extractor.model.MediaInfo
import com.alticode.feature.video.converter.databinding.FragmentVideoConverterBinding
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.framework.ui.components.SingleChoiceView
import com.alticode.platform.log.AppLog
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
    lateinit var mediaCodec: AltiMediaCodec

    private val videoEncoders by lazy { VideoEncoder.get() }
    private val audioEncoders by lazy { AudioEncoder.get() }
    private val mediaInfo: MediaInfo by lazy { mediaCodec.extractMedia(args.path) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // format
        binding.format.apply {
            title = "Format"
            optionList = videoEncoders
                .flatMap { it.fileTypes }
                .distinctBy { it.name }
                .map { SingleChoiceView.Option(it.name, it) }
            optionChangedListener = { onVideoFormatChanged() }
        }

        // audio codec
        binding.audioCodec.apply {
            title = "Audio codec"
            optionList = audioEncoders.map { SingleChoiceView.Option(it.name, it) }
            optionChangedListener = { onAudioEncoderChanged() }
        }

        // video codec
        binding.videoCodec.apply {
            val currentFileType = binding.format.selectedOption as FileType
            title = "Video codec"
            optionList = videoEncoders
                .filter { encoder -> encoder.isSupport(currentFileType.name) }
                .map { SingleChoiceView.Option(it.name, it) }
            optionChangedListener = { onVideoEncoderChanged() }
        }

        // resolution
        binding.resolution.apply {
            title = "Resolution"
            optionList = VideoResolution.get()
                .filter { filterResolution(it) }
                .map { SingleChoiceView.Option(it.name, it) }
            optionChangedListener = { onResolutionChanged() }
        }

        // frame rate
        binding.frameRate.apply {
            title = "Frame rate"
            optionList = FrameRate.get()
                .filter { filterFrameRate(it) }
                .map { SingleChoiceView.Option(it.name, it) }
        }

        // video bitrate
        binding.sliderVideoBitrate.apply {
            title = "Video Bitrate"
            val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
            valueFrom = videoEncoder.getBitrateSupported().lower.toFloat()
            valueTo = videoEncoder.getBitrateSupported().upper.toFloat()
            value = valueTo
            slider.addOnChangeListener { _, value, _ -> valueText = "$value bps" }
        }

        // audio bitrate
        binding.sliderAudioBitrate.apply {
            title = "Audio Bitrate"
            val audioEncoder = binding.audioCodec.getCurrentValue() as AudioEncoder
            valueFrom = audioEncoder.getBitrateSupported().lower.toFloat()
            valueTo = audioEncoder.getBitrateSupported().upper.toFloat()
            value = valueTo
            slider.addOnChangeListener { _, value, _ -> valueText = "$value bps" }
        }

        binding.btnConvert.setOnClickListener { convert() }
    }

    private fun onResolutionChanged() {
        binding.frameRate.apply {
            optionList = FrameRate.get()
                .filter { filterFrameRate(it) }
                .map { SingleChoiceView.Option(it.name, it) }
        }
    }

    private fun onVideoFormatChanged() {
        val currentFileType = binding.format.selectedOption as FileType
        binding.videoCodec.optionList = videoEncoders
            .filter { videoEncoder -> videoEncoder.isSupport(currentFileType.name) }
            .map { videoEncoder ->
                SingleChoiceView.Option(videoEncoder.name, videoEncoder)
            }
    }

    private fun onVideoEncoderChanged() {
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

    private fun onAudioEncoderChanged() {
        binding.sliderAudioBitrate.apply {
            val audioEncoder = binding.audioCodec.getCurrentValue() as AudioEncoder
            valueFrom = audioEncoder.getBitrateSupported().lower.toFloat()
            valueTo = audioEncoder.getBitrateSupported().upper.toFloat()
            value = valueTo
        }
    }

    private fun filterFrameRate(frameRate: FrameRate): Boolean {
        val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
        val resolution = getResolution()
        val fpsRange = videoEncoder.getSupportedFrameRatesFor(resolution.first, resolution.second)
        return frameRate.value >= fpsRange.lower && frameRate.value <= fpsRange.upper
    }

    private fun filterResolution(resolution: VideoResolution): Boolean {
        val videoEncoder = binding.videoCodec.getCurrentValue() as VideoEncoder
        return resolution.height <= videoEncoder.getHeightSupported().upper
    }

    private fun getResolution(): Pair<Int, Int> {
        val videoEncoder = binding.videoCodec.selectedOption as VideoEncoder
        val resolution = binding.resolution.selectedOption as VideoResolution
        val widthRange = videoEncoder.getSupportedWidthFor(resolution.height)
        val height = resolution.height
        val width = resolution.getWidth(mediaInfo.width, mediaInfo.height, widthRange)
        return Pair(width, height)
    }

    private fun getEncoderParam(): MediaEncoderParam {
        val format = binding.format.selectedOption as FileType
        val resolution = getResolution()
        val frameRate = binding.frameRate.selectedOption as FrameRate
        val videoBitrate = binding.sliderVideoBitrate.value.toInt()
        val audioBitrate = binding.sliderAudioBitrate.value.toInt()
        val videoEncoder = binding.videoCodec.selectedOption as VideoEncoder
        val audioEncoder = binding.audioCodec.selectedOption as AudioEncoder
        val outputPath = FileUtils.getOutputPath(File(args.path).name, format.name.lowercase())

        return MediaEncoderParam(
            inputPath = args.path,
            outputPath = outputPath,
            videoCodecName = videoEncoder.codecName,
            videoMimeType = videoEncoder.mimeType,
            videoEncoderProfile = videoEncoder.profile,
            width = resolution.first,
            height = resolution.second,
            frameRate = frameRate.value,
            videoBitRate = videoBitrate,
            audioCodecName = audioEncoder.codecName,
            audioMimeType = audioEncoder.mimeType,
            audioChannelCount = mediaInfo.audioChannelCount,
            audioSampleRate = mediaInfo.audioSampleRate,
            audioBitRate = audioBitrate
        )
    }

    private fun convert() {
        lifecycleScope.launch {
            val encoderParam = getEncoderParam()
            val videoEncoder = binding.videoCodec.selectedOption as VideoEncoder
            val isSupported = videoEncoder.areSizeAndRateSupported(encoderParam.width, encoderParam.height, encoderParam.frameRate)
            AppLog.d("Video isSupported? $isSupported")
            if (isSupported) {
                mediaCodec.encodeVideo(encoderParam)
            }
        }
    }
}