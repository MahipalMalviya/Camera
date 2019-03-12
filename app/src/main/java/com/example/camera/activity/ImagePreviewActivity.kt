package com.example.camera.activity

import IMAGE_PATH
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.camera.R
import com.example.camera.Utils.Utils
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity: AppCompatActivity(), View.OnClickListener {

    companion object {
        private lateinit var imagePath:String

        fun launch(context: Context,imagePath:String){
            val intent = Intent(context,ImagePreviewActivity::class.java)
            intent.putExtra(IMAGE_PATH,imagePath)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_image_preview)

        imagePath = intent.getStringExtra(IMAGE_PATH)

        preview_image.setImageBitmap(Utils().handleSamplingAndRotationBitmap(this,Uri.parse("file:///$imagePath")))

        setListener()
    }

    private fun setListener() {
        btn_image_capture_retry.setOnClickListener(this)
        btn_image_capture_ok.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_image_capture_retry -> {
                if (Utils().deleteFile(imagePath)){
                    finish()
                }
            }

            R.id.btn_image_capture_ok -> {
                Utils().addImageToGallery(imagePath,this)
                Toast.makeText(this,"Image Saved.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}