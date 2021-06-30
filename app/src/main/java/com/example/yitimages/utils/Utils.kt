package com.example.yitimages.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.yitimages.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object Utils {

    const val API_KEY = "19164538-fc92a7116c10b9954dec7ecd0"
    const val BASE_URL = "https://pixabay.com"

    fun shareImage(context: Context, bitmap: Bitmap) {
        try {
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs() // don't forget to make the directory
            val stream =
                FileOutputStream("$cachePath/image.png")
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                stream
            )
            stream.close()

            val imagePath = File(context.cacheDir, "images")
            val newFile = File(imagePath, "image.png")
            val contentUri: Uri? =
                FileProvider.getUriForFile(
                    context,
                    context.resources.getString(R.string.authority),
                    newFile
                )

            if (contentUri != null) {
                val shareIntent = Intent()
                shareIntent.apply {
                    action = Intent.ACTION_SEND
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    setDataAndType(
                        contentUri,
                        context.contentResolver.getType(contentUri)
                    )
                    putExtra(Intent.EXTRA_STREAM, contentUri)
                }

                context.startActivity(
                    Intent.createChooser(
                        shareIntent,
                        context.resources.getString(R.string.choose_app_text)
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}