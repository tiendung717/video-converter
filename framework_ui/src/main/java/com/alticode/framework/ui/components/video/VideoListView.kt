package com.alticode.framework.ui.components.video

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.airbnb.epoxy.EpoxyRecyclerView
import com.alticode.framework.ui.R
import com.alticode.framework.ui.components.video.adapter.AdapterFactory

class VideoListView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_video_list, this, true)
    }

    private val rvVideo: EpoxyRecyclerView by lazy { findViewById(R.id.rvVideo) }
    private val videoListController by lazy { AdapterFactory.createVideoListAdapter() }

    fun setVideoPathList(pathList: List<String>) {
        rvVideo.setController(videoListController)
        videoListController.setData(pathList)
    }
}