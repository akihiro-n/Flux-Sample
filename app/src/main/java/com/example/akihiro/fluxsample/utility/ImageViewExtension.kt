package com.example.akihiro.fluxsample.utility

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

@BindingAdapter("app:imageUrl")
fun ImageView.imageUrl(url: String?) {
    url?: return
    GlobalScope.launch(Dispatchers.Unconfined) {
        setImageBitmap(fetchBitmapAsync(url).await())
    }
}

private fun fetchBitmapAsync(url: String) = GlobalScope.async(Dispatchers.Default) {
    var bitmap: Bitmap? = null
    (URL(url).openConnection() as HttpURLConnection)
        .also { it.setRequestConfiguration() }
        .also { it.connect() }
        .also {
            if (it.responseCode == HttpURLConnection.HTTP_OK)
                it.inputStream
                    .let { inputStream ->
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                    }
        }
        .also { it.disconnect() }
    return@async bitmap
}

private fun HttpURLConnection.setRequestConfiguration() {
    also { httpURLConnection ->
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.setRequestProperty("Accept-Language", "jp")
        httpURLConnection.readTimeout = 20000
        httpURLConnection.connectTimeout = 20000
        httpURLConnection.instanceFollowRedirects = false
    }
}