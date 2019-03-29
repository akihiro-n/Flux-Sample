package com.example.akihiro.fluxsample

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.akihiro.fluxsample.databinding.ActivityMainBinding
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val useCase by inject<ItemUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        useCase.fetchNewItems(1)
            .subscribeOn(Schedulers.io())
            .subscribe({

            },{})
    }
}
