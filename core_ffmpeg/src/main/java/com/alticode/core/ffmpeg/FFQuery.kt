package com.alticode.core.ffmpeg

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alticode.platform.log.AppLog
import com.alticode.platform.utils.toPrettySize
import com.alticode.platform.utils.toTime
import com.simform.videooperations.CallBackOfQuery
import com.simform.videooperations.FFmpegCallBack
import com.simform.videooperations.LogMessage
import com.simform.videooperations.Statistics
import java.util.*

internal data class FFQuery(
    val activity: AppCompatActivity,
    val input: String,
    val output: String,
    val params: LinkedList<Array<String>>,
    val ffCallback: FFCallback
) {

    fun run() {
        val paramIterator = params.iterator()
        if (paramIterator.hasNext()) {
            val params = paramIterator.next()
            val tempOutput = if (paramIterator.hasNext()) getTempPath(activity) else output
            val query = appendInOutParam(input, tempOutput, params)

            val callback = QueryCallback(tempOutput, paramIterator, ffCallback)
            CallBackOfQuery().callQuery(activity, query, callback)
        }
    }

    private fun appendInOutParam(
        input: String,
        output: String,
        query: Array<String>
    ): Array<String> {
        return mutableListOf<String>().apply {
            add("-i")
            add(input)
            addAll(query)
            add("-y")
            add(output)
        }.toTypedArray()
    }

    inner class DelegatedCallback(private val ffCallback: FFCallback) : FFmpegCallBack {
        override fun success() {
            ffCallback.onSuccess()
        }

        override fun failed() {
            ffCallback.onFailed()
        }

        override fun cancel() {
            ffCallback.onCancel()
        }

        override fun process(logMessage: LogMessage) {
            AppLog.i(logMessage.text)
        }

        override fun statisticsProcess(statistics: Statistics) {
            val time = statistics.time.toLong().toTime()
            val size = statistics.size.toPrettySize()
            ffCallback.onProgress("$time - $size")
        }
    }

    open inner class QueryCallback(
        private val tempOutput: String,
        private val paramIterator: Iterator<Array<String>>,
        ffCallback: FFCallback
    ) : FFmpegCallBack by DelegatedCallback(ffCallback) {
        override fun success() {
            if (paramIterator.hasNext()) {
                val params = paramIterator.next()
                val tempPath = if (paramIterator.hasNext()) getTempPath(activity) else output
                val query = appendInOutParam(tempOutput, tempPath, params)
                val callback = QueryCallback(tempPath, paramIterator, ffCallback)
                CallBackOfQuery().callQuery(activity, query, callback)
            } else {
                ffCallback.onSuccess()
            }
        }
    }

    private fun getTempPath(context: Context) =
        "${context.cacheDir.absolutePath}/${UUID.randomUUID()}.mp4"
}