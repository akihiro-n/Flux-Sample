package com.example.akihiro.fluxsample

import android.app.Application
import com.example.akihiro.fluxsample.application.actioncreator.ItemActionCreator
import flux.Dispatcher
import com.example.akihiro.fluxsample.domain.repository.ItemRepository
import com.example.akihiro.fluxsample.domain.repository.ItemRepositoryImpl
import com.example.akihiro.fluxsample.domain.store.ItemStore
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCaseImpl
import com.example.akihiro.fluxsample.infra.RetrofitClient
import com.example.akihiro.fluxsample.ui.MainViewModel
import flux.Action
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

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
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                clientModule,
                repositoryModule,
                useCaseModule,
                actionCreatorModule,
                dispatcherModule,
                storeModule,
                viewModelModule
            )
        }
    }

    private val clientModule: Module by lazy {
        module {
            single { RetrofitClient() }
        }
    }

    private val repositoryModule: Module by lazy {
        module {
            single<ItemRepository> { ItemRepositoryImpl(get()) }
        }
    }

    private val useCaseModule: Module by lazy {
        module {
            single<ItemUseCase> { ItemUseCaseImpl(get()) }
        }
    }

    private val actionCreatorModule: Module by lazy {
        module {
            single { ItemActionCreator(get()) }
        }
    }

    private val dispatcherModule: Module by lazy {
        module {
            single<Dispatcher> { PublishSubject.create<Action>() }
        }
    }

    private val storeModule: Module by lazy {
        module {
            single { ItemStore() }
        }
    }

    private val viewModelModule: Module by lazy {
        module {
            single { MainViewModel(get(), get()) }
        }
    }
}