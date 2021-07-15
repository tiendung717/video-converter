package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.goodmood.platform.log.AppLog
import com.simform.videooperations.CallBackOfQuery
import com.simform.videooperations.FFmpegCallBack
import com.simform.videooperations.LogMessage
import com.simform.videooperations.Statistics
import java.lang.StringBuilder

class FFMpegExecutorImpl : FFmpegExecutor {

    override fun run(
        activity: AppCompatActivity,
        inputPath: String,
        outputPath: String,
        filters: List<FFmpegFilter>,
        ffmpegCallback: FFCallback
    ) {
        val query = buildQuery(inputPath, outputPath, filters)

        if (BuildConfig.DEBUG) {
            dumpFFmpegCommand(query)
        }

        val normalCallback = object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {
                AppLog.i(logMessage.toString())
            }

            override fun statisticsProcess(statistics: Statistics) {
                super.statisticsProcess(statistics)
                ffmpegCallback.onProgress("${statistics.time} - ${statistics.size}")
            }

            override fun success() {
                ffmpegCallback.onSuccess()
            }

            override fun failed() {
                ffmpegCallback.onFailed()
            }

            override fun cancel() {
                ffmpegCallback.onCancel()
            }
        }

        val mergeCallback = object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {
                AppLog.i(logMessage.toString())
            }

            override fun statisticsProcess(statistics: Statistics) {
                super.statisticsProcess(statistics)
                ffmpegCallback.onProgress("${statistics.time} - ${statistics.size}")
            }

            override fun success() {
                if (query.normalQuery.isNotEmpty()) {
                    CallBackOfQuery().callQuery(activity, query.normalQuery, normalCallback)
                }
            }

            override fun failed() {
                ffmpegCallback.onFailed()
            }

            override fun cancel() {
                ffmpegCallback.onCancel()
            }
        }

        if (query.mergeQuery.isNotEmpty()) {
            CallBackOfQuery().callQuery(activity, query.mergeQuery, mergeCallback)
        } else if (query.normalQuery.isNotEmpty()) {
            CallBackOfQuery().callQuery(activity, query.normalQuery, normalCallback)
        }
    }

    private fun buildQuery(
        inputPath: String,
        outputPath: String,
        filters: List<FFmpegFilter>
    ): FFQuery {
        val mergeFilters = filters.filterIsInstance<FFmpegMergeFilter>()
        val normalFilters = filters.filterIsInstance<FFmpegNormalFilter>()

        val mergeQuery = mutableListOf<String>().apply {
            add("-i")
            add(inputPath)
            mergeFilters.forEach { addAll(it.getParams()) }
            add("-preset")
            add("ultrafast")
            add("-y")
            add(outputPath)
        }.toTypedArray()

        val normalQuery = mutableListOf<String>().apply {
            add("-i")
            add(inputPath)
            normalFilters.forEach { addAll(it.getParams()) }
            add("-preset")
            add("ultrafast")
            add("-y")
            add(outputPath)
        }.toTypedArray()


        return FFQuery(mergeQuery, normalQuery)
    }

    private fun dumpFFmpegCommand(query: FFQuery) {
        if (BuildConfig.DEBUG) {
            val stringBuilder = StringBuilder()
            query.mergeQuery.forEach { stringBuilder.append(it).append(" ") }
            AppLog.i("Merge query: ${stringBuilder.toString()}")

            stringBuilder.clear()
            query.normalQuery.forEach { stringBuilder.append(it).append(" ") }
            AppLog.i("Normal query: ${stringBuilder.toString()}")
        }
    }
}