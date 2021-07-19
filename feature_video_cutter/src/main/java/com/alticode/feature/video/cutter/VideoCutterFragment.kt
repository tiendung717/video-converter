package com.alticode.feature.video.cutter

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alticode.core.ui.base.BaseFragment
import com.alticode.feature.video.R
import com.alticode.feature.video.databinding.FragmentVideoCutterBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.io.File

class VideoCutterFragment : BaseFragment<FragmentVideoCutterBinding>(R.layout.fragment_video_cutter) {

    private val args: VideoCutterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.videoView.player = SimpleExoPlayer.Builder(requireContext()).build().apply {
            val mediaUri = Uri.fromFile(File(args.path))
            val mediaItem = MediaItem.fromUri(mediaUri)
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
        }
    }
}