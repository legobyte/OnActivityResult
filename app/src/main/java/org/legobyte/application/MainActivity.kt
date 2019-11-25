package org.legobyte.application

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.legobyte.onactivityresult.Proxy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectPictureButton.setOnClickListener {

            Proxy.with(this).listener { requestCode, resultCode, data ->
                textView.text = "requestCode: $requestCode\nresultCode: $resultCode\nintent: $data"
                if(resultCode == Activity.RESULT_OK){
                    imageView.visibility = View.VISIBLE
                    imageView.setImageURI(data!!.data)
                }else if(resultCode == Proxy.RESULT_INTENT_UNHANDLED){
                    textView.text = "Looks like you dont have any FileManager or Gallery application installed to pick image!"
                }
            }.launch(Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" })

        }


    }
}
