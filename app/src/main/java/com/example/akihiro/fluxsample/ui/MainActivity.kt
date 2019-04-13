package com.example.akihiro.fluxsample.ui

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.akihiro.fluxsample.MyApplication
import com.example.akihiro.fluxsample.R
import com.example.akihiro.fluxsample.databinding.ActivityMainBinding
import com.example.akihiro.fluxsample.utility.pageNationListener
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initItemsRecyclerView()
        observeLiveData()
    }

    private fun initViewModel() {
        viewModel.onCreate()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initItemsRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(MyApplication.getApplication)
            adapter = viewModel.adapter
            pageNationListener(18) {
                viewModel.fetchNewItems()
            }
        }
    }

    private fun observeLiveData() {
        viewModel.items.observe(this, Observer {
            viewModel.adapter.notifyDataSetChanged()
        })
    }
}
