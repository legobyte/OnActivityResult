package org.legobyte.onactivityresult

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import androidx.annotation.CheckResult


object Proxy {
    @JvmStatic
    val mainThreadHandler by lazy { Handler(Looper.getMainLooper()) }
    const val KEY_INTENT = "sunrise:intent"
    const val KEY_REQUEST_CODE = "sunrise:request-code"
    const val KEY_RESULT_RECEIVER = "sunrise:result-receiver"
    const val DEFAULT_REQUEST_CODE = 10
    const val RESULT_INTENT_UNHANDLED = -10

    fun with(context: Context): ContextBucket {
        return ContextBucket(context)
    }
}

data class ContextBucket(internal val context: Context) {
    @CheckResult
    fun listener(resultReceiver: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit)
            = Launcher(this, resultReceiver)
}
data class Launcher(private val contextBucket: ContextBucket, private val resultReceiver: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit){
    fun launch(intent: Intent, requestCode: Int= Proxy.DEFAULT_REQUEST_CODE){
        val receiver = object: ResultReceiver(Proxy.mainThreadHandler){
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                val resultIntent = resultData?.getParcelable<Intent>(Proxy.KEY_INTENT)
                resultReceiver(requestCode, resultCode, resultIntent)
            }
        }
        val context = when {
            contextBucket.context is Activity -> contextBucket.context
            contextBucket.context is ContextWrapper -> contextBucket.context.baseContext ?: contextBucket.context
            else -> contextBucket.context
        }
        context.startActivity(Intent(context, ProxyActivity::class.java).apply {
            if(context !is Activity){
                // this is not an activity context
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            putExtra(Proxy.KEY_INTENT, intent)
            putExtra(Proxy.KEY_REQUEST_CODE, requestCode)
            putExtra(Proxy.KEY_RESULT_RECEIVER, receiver)
        })
    }
}
