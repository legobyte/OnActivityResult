package org.sunrse.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.sunrise.onactivityresult.Proxy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Proxy.with(context = applicationContext).listener { requestCode, resultCode, data ->
            textView.text = "requestCode: $requestCode\nresultCode: $resultCode\nintent: $data"
        }.launch(Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" })

    }
}
