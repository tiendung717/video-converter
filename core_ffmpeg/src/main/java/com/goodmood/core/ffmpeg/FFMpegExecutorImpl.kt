package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.goodmood.platform.log.AppLog
import com.simform.videooperations.CallBackOfQuery
import com.simform.videooperations.FFmpegCallBack
import com.simform.videooperations.LogMessage
import com.simform.videooperations.Statistics

class FFMpegExecutorImpl : FFmpegExecutor {

    override fun run(activity: AppCompatActivity, command: Array<String>, ffmpegCallback: FFCallback) {
        CallBackOfQuery().callQuery(activity, command, object : FFmpegCallBack {
            override fun process(logMessage: LogMessage) {
                AppLog.i(logMessage.toString())
            }

            override fun statisticsProcess(statistics: Statistics) {
                super.statisticsProcess(statistics)
                ffmpegCallback.onProgress("Frame ${statistics.videoFrameNumber} - ${statistics.bitrate}")
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
}