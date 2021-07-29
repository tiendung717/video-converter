package com.alticode.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.simform.videooperations.CallBackOfQuery
import java.util.*

class FFMpegExecutorImpl : FFmpegExecutor {

    override fun run(
        activity: AppCompatActivity,
        inputPath: String,
        outputPath: String,
        steps: List<FFStep>,
        ffCallback: FFCallback
    ) {
        val cmdLinkedList = LinkedList<Array<String>>()
        steps.forEach {
            cmdLinkedList.add(it.getParams().toTypedArray())
        }

        val query = FFQuery(activity, inputPath, outputPath, cmdLinkedList, ffCallback)
        query.run()
    }

    override fun cancel() {
        CallBackOfQuery().cancelProcess()
    }
}