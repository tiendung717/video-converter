package com.alticode.framework.ui.components.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.alticode.framework.ui.R
import com.alticode.framework.ui.components.video.adapter.AdapterFactory
import com.alticode.framework.ui.components.video.adapter.OnVideoRemoveListener

class VideoListView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_list, this, true)
    }

    private val videoPathList = mutableListOf<String>()
    private val rvVideo: EpoxyRecyclerView by lazy { findViewById(R.id.rvVideo) }
    private val videoListController by lazy { AdapterFactory.createVideoListAdapter(onVideoRemoveListener) }
    private val onVideoRemoveListener: OnVideoRemoveListener = {
        videoPathList.remove(it)
        updateUi()
    }

    fun setVideoPathList(pathList: List<String>) {
        videoPathList.addAll(pathList)

        val rvOrientation = if (orientation == HORIZONTAL) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
        rvVideo.layoutManager = LinearLayoutManager(context, rvOrientation, false)
        rvVideo.setController(videoListController)
        updateUi()
    }

    fun getVideoPathList() = videoPathList.toList()

    private fun updateUi() {
        val rvOrientation = if (orientation == HORIZONTAL) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
        videoListController.setData(rvOrientation, videoPathList)
    }
}