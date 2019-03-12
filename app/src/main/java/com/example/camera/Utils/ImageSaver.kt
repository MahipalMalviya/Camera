package com.example.camera.Utils

import android.content.Context
import android.media.Image
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Saves a JPEG [Image] into the specified [File].
 */
internal class ImageSaver(
        /**
         * The JPEG image
         */
        private val image: Image,
        private val callback:OnImageSaveListener
) : Runnable {

    override fun run() {
        val file = Utils().createFile()

        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        var output: FileOutputStream? = null
        try {
            output = FileOutputStream(file)
            output.write(bytes)

        } catch (e: Throwable) {
            Log.e(TAG, e.toString())
        } finally {
            image.close()
            output?.let {
                try {
                    it.close()
                    callback.onImageSaveSuccessful(file.absolutePath)
                } catch (e: IOException) {
                    Log.e(TAG, e.toString())
                }
            }
        }
    }

    companion object {
        /**
         * Tag for the [Log].
         */
        private val TAG = "ImageSaver"
    }



    interface OnImageSaveListener{
        fun onImageSaveSuccessful(imagePath:String?)
    }
}
