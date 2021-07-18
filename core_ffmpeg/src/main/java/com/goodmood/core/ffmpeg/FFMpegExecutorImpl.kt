package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.goodmood.core.ffmpeg.filter.FFmpegFilter
import com.goodmood.core.ffmpeg.filter.FFmpegMergeFilter
import com.goodmood.core.ffmpeg.filter.FFmpegNormalFilter
import com.goodmood.platform.log.AppLog
import com.simform.videooperations.CallBackOfQuery
import java.lang.StringBuilder
import java.util.*

class FFMpegExecutorImpl : FFmpegExecutor {

    override fun run(
        activity: AppCompatActivity,
        inputPath: String,
        outputPath: String,
        filters: List<FFmpegFilter>,
        ffCallback: FFCallback
    ) {
        val mergeFilters = filters.filterIsInstance<FFmpegMergeFilter>()
        val normalFilters = filters.filterIsInstance<FFmpegNormalFilter>()

        val mergeQuery = mutableListOf<String>().apply {
            mergeFilters.forEach { addAll(it.getParams()) }
        }.toTypedArray()

        val normalQuery = mutableListOf<String>().apply {
            normalFilters.forEach { addAll(it.getParams()) }
        }.toTypedArray()

        val cmdLinkedList = LinkedList<Array<String>>()
        if (mergeQuery.isNotEmpty()) cmdLinkedList.add(mergeQuery)
        if (normalQuery.isNotEmpty()) cmdLinkedList.add(normalQuery)

        val query = FFQuery(activity, inputPath, outputPath, cmdLinkedList, ffCallback)
        query.run()
    }

    override fun cancel() {
        CallBackOfQuery().cancelProcess()
    }
}