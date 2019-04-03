package com.example.akihiro.fluxsample.ui

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.akihiro.fluxsample.R
import com.example.akihiro.fluxsample.databinding.ActivityMainBinding
import com.example.akihiro.fluxsample.utility.textChangedAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.onCreate()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = viewModel.adapter
        }



        launch(Dispatchers.Unconfined) {
            binding.editText
                .textChangedAsync(this)
                .await()
                .consumeEach { text ->
                    Log.v("callback", text) //フォームから入力された値がコールバックで受け取れる
                }
        }

    }
}
