package com.alticode.feature.video.cutter

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alticode.core.extractor.AltiMediaCodec
import com.alticode.core.ffmpeg.FFCallback
import com.alticode.core.ffmpeg.FFStepExtension
import com.alticode.core.ffmpeg.FFmpegExecutor
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.feature.video.R
import com.alticode.feature.video.databinding.FragmentVideoCutterBinding
import com.alticode.framework.ui.components.DropDownMenu
import com.alticode.framework.ui.viewmodel.UniverseViewModel
import com.alticode.platform.log.AppLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class VideoCutterFragment : BaseFragment<FragmentVideoCutterBinding>(R.layout.fragment_video_cutter) {

    private val args: VideoCutterFragmentArgs by navArgs()
    private val universeViewModel: UniverseViewModel by activityViewModels()

    @Inject
    lateinit var ffmpegExecutor: FFmpegExecutor

    @Inject
    lateinit var mediaCodec: AltiMediaCodec

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.videoView.setVideoPath(args.path)
        binding.videoListView.setVideoPathList(listOf(args.path))
        binding.dropdownMenu.setItems(listOf(
            DropDownMenu.Item("One", 1),
            DropDownMenu.Item("Two", 2),
            DropDownMenu.Item("Three", 3),
            DropDownMenu.Item("Four", 4)
        ), DropDownMenu.Item("Two", 2))


        binding.btnTrim.setOnClickListener {
            doTrim()
        }
    }

    private fun doTrim() {
        val trimStep = FFStepExtension.cutVideo("00:00:22", "00:00:30")
        ffmpegExecutor.run(activity as AppCompatActivity, args.path, output(), listOf(trimStep), object : FFCallback {
            override fun onProgress(progress: String) {
                AppLog.i("onProgress: $progress")
            }

            override fun onSuccess() {
                AppLog.i("DONE")
                lifecycleScope.launch { universeViewModel.saveOutput(com.alticode.core.data.domain.model.Media(args.path)) }
            }
        })
    }

    private fun output(): String {
        val dirPath = "${Environment.getExternalStorageDirectory().absolutePath}/converter"
        val outputDir = File(dirPath)
        if (!outputDir.exists()) outputDir.mkdir()

        val randomName = UUID.randomUUID().toString().take(10).lowercase()
        return "$dirPath/${randomName}.mp4"
    }
}