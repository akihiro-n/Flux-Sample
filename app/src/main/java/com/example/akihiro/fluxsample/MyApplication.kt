package com.example.akihiro.fluxsample

import android.app.Application
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        lateinit var getApplication: MyApplication
            private set
    }

    init {
        getApplication = this@MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {  }
    }
}