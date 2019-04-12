package com.example.akihiro.fluxsample.utility

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import java.net.HttpURLConnection
import java.net.URL

/**
 * ImageViewクラスのExtension
 * URL取得した画像を、ImageViewに表示させる
 * @param url
 */
@BindingAdapter("app:imageUrl")
fun ImageView.imageUrl(url: String?) {
    url?: return
    GlobalScope.launch(Dispatchers.Main) {
        setImageBitmap(fetchBitmapAsync(url).await())
    }
}

/**
 * Http通信を非同期で行い、画像をBitmap形式で取得する
 * @param url
 * @return
 */
private fun fetchBitmapAsync(url: String): Deferred<Bitmap?> = GlobalScope.async(Dispatchers.Default) {
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

/**
 * Http通信に関する設定を定義
 */
private fun HttpURLConnection.setRequestConfiguration() {
    also { httpURLConnection ->
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.setRequestProperty("Accept-Language", "jp")
        httpURLConnection.readTimeout = 20000
        httpURLConnection.connectTimeout = 20000
        httpURLConnection.instanceFollowRedirects = false
    }
}