package com.alticode.framework.ui.components.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.alticode.framework.ui.R
import com.alticode.framework.ui.components.video.adapter.AdapterFactory
import com.alticode.framework.ui.components.video.adapter.OnVideoRemoveListener

class VideoListView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_list, this, true)
    }

    private val videoPathList = mutableListOf<String>()
    private val rvVideo: EpoxyRecyclerView by lazy { findViewById(R.id.rvVideo) }
    private val videoListController by lazy { AdapterFactory.createVideoListAdapter(onVideoRemoveListener) }
    private val onVideoRemoveListener: OnVideoRemoveListener = {
        videoPathList.remove(it)
        videoListController.setData(videoPathList)
    }

    fun setVideoPathList(pathList: List<String>) {
        videoPathList.addAll(pathList)
        rvVideo.setController(videoListController)
        videoListController.setData(videoPathList)
    }

    fun getVideoPathList() = videoPathList.toList()
}