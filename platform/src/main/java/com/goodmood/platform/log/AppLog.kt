package com.goodmood.platform.log

import android.util.Log

object AppLog {
    private const val TAG = "AppLog"
    fun d(msg: String) {
        Log.d(TAG, codeLocation + msg)
    }

    fun i(msg: String) {
        Log.i(TAG, codeLocation + msg)
    }

    fun w(msg: String) {
        Log.w(TAG, codeLocation + msg)
    }

    fun e(msg: String) {
        Log.e(TAG, codeLocation + msg)
    }

    fun v(msg: String) {
        Log.v(TAG, codeLocation + msg)
    }

    fun printStackTrace(throwable: Throwable) {
        Log.e(
            TAG,
            "**************************************************************************************************************************************************************************************"
        )
        for (ste in throwable.stackTrace) {
            Log.d(TAG, ste.toString())
        }
        Log.e(
            TAG,
            "**************************************************************************************************************************************************************************************"
        )
    }

    private val codeLocation: String
        get() {
            val cthread = Thread.currentThread()
            val fullClassName = cthread.stackTrace[4].className
            val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
            val methodName = cthread.stackTrace[4].methodName
            val lineNumber = cthread.stackTrace[4].lineNumber
            return "$className.$methodName():$lineNumber: "
        }
}

