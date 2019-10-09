package org.legobyte.onactivityresult

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver

internal class ProxyActivity : Activity() {


    private fun exception(initialMessage:String):Throwable{
        return IllegalArgumentException("$initialMessage You should start this activity with org.sunrise.onactivityresult.Proxy#with method!")
    }

    private val clientIntent by lazy {
        intent.getParcelableExtra<Intent>(Proxy.KEY_INTENT) ?: throw exception("Intent cannot be null.")
    }
    private val resultReceiver by lazy {
        intent.getParcelableExtra<ResultReceiver>(Proxy.KEY_RESULT_RECEIVER) ?: throw exception("ResultReceiver cannot be null.")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        // Set a Translucent NoTitleBar theme before calling super.onCreate()
        setTheme(android.R.style.Theme_Translucent_NoTitleBar)
        super.onCreate(savedInstanceState)

        if(clientIntent.resolveActivity(packageManager) == null){
            resultReceiver.send(Proxy.RESULT_INTENT_UNHANDLED, null)
            finish()
        }else{
            startActivityForResult(clientIntent, intent.getIntExtra(Proxy.KEY_REQUEST_CODE, Proxy.DEFAULT_REQUEST_CODE))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        resultReceiver.send(resultCode, Bundle().apply {
            if(data!=null)
                putParcelable(Proxy.KEY_INTENT, data)
        })
        finish()
    }


}
