package com.alticode.videoeditor.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alticode.core.data.domain.model.Media
import com.alticode.core.data.presentation.viewmodel.VideoViewModel
import com.alticode.framework.ui.base.BaseFragment
import com.alticode.platform.utils.hasGranted
import com.alticode.videoeditor.R
import com.alticode.videoeditor.adapter.AdapterFactory
import com.alticode.videoeditor.adapter.VideoAdapterController
import com.alticode.videoeditor.databinding.FragmentMediaOutputBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File


@AndroidEntryPoint
class MediaOutputFragment : BaseFragment<FragmentMediaOutputBinding>(R.layout.fragment_media_output) {

    private lateinit var videoAdapter: VideoAdapterController
    private val videoViewModel: VideoViewModel by activityViewModels()

    // Request read & write external storage permission, then open the file explorer if permission are granted.
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var granted = true
            it.forEach { ret -> granted = granted and ret.value }
            if (granted) pickVideoRequest.launch("video/*")
        }

    // Open file system explorer to select a video for editing
    private val pickVideoRequest =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val path = getVideoPath(uri)
            if (path != null) {
                val editorDeepLink = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse("nav://video.converter/?path=$path"))
                    .build()
                findNavController().navigate(editorDeepLink)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoAdapter = AdapterFactory.createVideoAdapter { openVideo(it) }
        binding.rvVideo.setController(videoAdapter)
        binding.btnNewVideo.setOnClickListener { selectVideo() }

        lifecycleScope.launch {
            videoViewModel.videoFlow.collect {
                updateVideoList(it)
            }
        }

        videoViewModel.loadAllOutputMedia()
    }

    private fun updateVideoList(mediaList: List<Media>) {
        mediaList.apply {
            binding.rvVideo.layoutManager =
                if (isEmpty()) LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                else GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

            videoAdapter.setData(this)
        }
    }

    private fun selectVideo() {
        if (hasPermission()) {
            pickVideoRequest.launch("video/*")
        } else {
            permissionRequest.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
    }

    private fun hasPermission(): Boolean {
        return Manifest.permission.READ_EXTERNAL_STORAGE.hasGranted(requireContext())
                && Manifest.permission.WRITE_EXTERNAL_STORAGE.hasGranted(requireContext())
    }

    private fun getVideoPath(uri: Uri): String? {
        var path: String? = null
        val docId = DocumentsContract.getDocumentId(uri)
        val videoId = docId.split(":")[1]

        val cursor = activity?.contentResolver?.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            null,
            "_id=?",
            arrayOf(videoId),
            null
        )
        if (cursor != null) {
            if (cursor.count > 0) {
                cursor.moveToFirst()
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA))
            }
            cursor.close()
        }

        return path
    }

    private fun openVideo(media: Media) {
        val videoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            File(media.path)
        )
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(videoUri, "video/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(this)
        }
    }
}
