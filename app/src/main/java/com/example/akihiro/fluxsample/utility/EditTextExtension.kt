package com.example.akihiro.fluxsample.utility

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable

/**
 * EditTextクラスの拡張
 * 入力された文字列を流すObservableを返す
 * @return
 */
fun EditText.toObservable(): Observable<String> = Observable
    .create { observableEmitter ->
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                editable?.let {
                    observableEmitter.onNext("$it")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }