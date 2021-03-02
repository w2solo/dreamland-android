package com.w2solo.android.utils

import android.util.Log

object Logger {
    private var enabled = false

    private val LOG_PREFIX = "Dreamland---"

    fun isEnabled(): Boolean {
        return enabled
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun e(TAG: String?, msg: String) {
        if (isEnabled()) {
            Log.e(TAG, "$LOG_PREFIX$msg")
        }
    }

    fun w(TAG: String?, msg: String) {
        if (isEnabled()) {
            Log.w(TAG, "$LOG_PREFIX$msg")
        }
    }

    fun i(TAG: String?, msg: String) {
        if (isEnabled()) {
            Log.i(TAG, "$LOG_PREFIX$msg")
        }
    }

    fun d(TAG: String?, msg: String) {
        if (isEnabled()) {
            Log.i(TAG, "$LOG_PREFIX$msg")
        }
    }

    fun v(TAG: String?, msg: String) {
        if (isEnabled()) {
            Log.i(TAG, "$LOG_PREFIX$msg")
        }
    }
}