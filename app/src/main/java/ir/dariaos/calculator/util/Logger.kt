package ir.dariaos.calculator.util

import android.util.Log
import ir.dariaos.calculator.BuildConfig.DEBUG

// Tag for logs
const val TAG = "CalculatorPlus"

fun printLogD(className: String, message: String?) {
    if (DEBUG) {
        Log.d("$TAG:DEBUG", "$className: $message")
    }
}

fun printLogE(className: String, message: String?) {
    if (DEBUG) {
        Log.d("$TAG:DEBUG", "$className: $message")
    } /*else {
        message?.let {
            FirebaseCrashlytics.getInstance().log("$className: $it")
        }
    }*/
}