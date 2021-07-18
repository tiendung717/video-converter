package com.goodmood.videoeditor.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goodmood.core.data.domain.model.Video
import com.goodmood.core.data.presentation.viewmodel.VideoViewModel
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.platform.log.AppLog
import com.goodmood.platform.utils.hasGranted
import com.goodmood.videoeditor.adapter.AdapterFactory
import com.goodmood.videoeditor.adapter.VideoAdapterController
import com.goodmood.videoeditor.databinding.FragmentVideoListBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File


@AndroidEntryPoint
class VideoListFragment : BaseFragment() {

    private lateinit var binding: FragmentVideoListBinding
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
                    .fromUri(Uri.parse("nav://editor/?videoPath=$path"))
                    .build()
                findNavController().navigate(editorDeepLink)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoAdapter = AdapterFactory.createVideoAdapter { openVideo(it) }
        binding.rvVideo.setController(videoAdapter)
        binding.btnNewVideo.setOnClickListener { selectVideo() }

        disposable.add(
            videoViewModel.getAllVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { updateVideoList(it) },
                    { AppLog.e("Can't load video list. Check error: ${it.message}") }
                )
        )
    }

    private fun updateVideoList(videoList: List<Video>) {
        if (videoList.isEmpty()) {
            binding.rvVideo.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        } else {
            binding.rvVideo.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        }
        videoAdapter.setData(videoList)
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

    private fun openVideo(video: Video) {
        val videoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            File(video.path)
        )
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(videoUri, "video/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(this)
        }
    }
}
