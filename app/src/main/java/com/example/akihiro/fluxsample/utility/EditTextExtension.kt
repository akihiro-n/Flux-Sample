package com.example.akihiro.fluxsample.utility

import android.text.Editable
import android.widget.EditText
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.sendBlocking

/**
 * EditTextのExtension
 * @param coroutineScope
 * @return
 * ＜使用例＞
 * launch(Dispatchers.Unconfined) {
 *     binding.editText
 *         .textChangedAsync(this)
 *         .await()
 *         .consumeEach { text ->
 *             Log.v("callback", text) //フォームから入力された値がコールバックで受け取れる
 *         }
 * }
 */
fun EditText.textChangedAsync(coroutineScope: CoroutineScope): Deferred<ReceiveChannel<String>> =
    BroadcastChannel<String>(1)
        .also { broadcastChannel ->
            val textWatcher = TextWatcher(broadcastChannel)
            this@textChangedAsync.addTextChangedListener(textWatcher)
        }
        .let { broadcastChannel ->
            coroutineScope.async(Dispatchers.Unconfined) {
                broadcastChannel.openSubscription()
            }
        }

/**
 * TextWatcherを継承したクラス
 * BroadcastChannel<String>を渡す
 * @param broadcastChannel
 */
private class TextWatcher(private val broadcastChannel: BroadcastChannel<String>) : android.text.TextWatcher {
    override fun afterTextChanged(editable: Editable?) {
        editable ?: return
        broadcastChannel.sendBlocking("$editable")
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}