package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.goodmood.platform.log.AppLog
import com.simform.videooperations.CallBackOfQuery
import com.simform.videooperations.FFmpegCallBack
import com.simform.videooperations.LogMessage
import com.simform.videooperations.Statistics
import java.lang.StringBuilder

class FFMpegExecutorImpl : FFmpegExecutor {

    override fun run(activity: AppCompatActivity, inputPath: String, outputPath: String, filters: List<FFmpegFilter>, ffmpegCallback: FFCallback){
        val query = buildQuery(inputPath, outputPath, filters)

        if (BuildConfig.DEBUG) {
            dumpFFmpegCommand(query)
        }

        CallBackOfQuery().callQuery(activity, query, object : FFmpegCallBack {
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
        })
    }

    private fun buildQuery(inputPath: String, outputPath: String, filters: List<FFmpegFilter>): Array<String> {
        val cmd = mutableListOf("-i", inputPath)

        filters.forEach {
            cmd.addAll(it.getParams())
        }

        cmd.apply {
            add("-preset")
            add("ultrafast")
            add(outputPath)
        }
        return cmd.toTypedArray()
    }

    private fun dumpFFmpegCommand(cmd: Array<String>) {
        if (BuildConfig.DEBUG) {
            val stringBuilder = StringBuilder()
            cmd.forEach { stringBuilder.append(it).append(" ") }
            AppLog.d("Command: ${stringBuilder.toString()}")
        }
    }
}