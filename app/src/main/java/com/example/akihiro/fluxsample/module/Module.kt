package com.example.akihiro.fluxsample.module

import com.example.akihiro.fluxsample.MyApplication
import com.example.akihiro.fluxsample.R
import com.example.akihiro.fluxsample.application.actioncreator.ItemActionCreator
import com.example.akihiro.fluxsample.domain.repository.ItemRepository
import com.example.akihiro.fluxsample.domain.repository.ItemRepositoryImpl
import com.example.akihiro.fluxsample.domain.store.ItemStore
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCase
import com.example.akihiro.fluxsample.domain.usecase.ItemUseCaseImpl
import com.example.akihiro.fluxsample.infra.API
import com.example.akihiro.fluxsample.ui.MainViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import flux.Action
import flux.Dispatcher
import io.reactivex.subjects.PublishSubject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val clientModule: Module by lazy {
    module {
        single<API> {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val httpLoggingInterceptor = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(MyApplication.getApplication.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(API::class.java)
        }
    }
}

val repositoryModule: Module by lazy {
    module {
        single<ItemRepository> { ItemRepositoryImpl(get()) }
    }
}

val useCaseModule: Module by lazy {
    module {
        single<ItemUseCase> { ItemUseCaseImpl(get()) }
    }
}

val actionCreatorModule: Module by lazy {
    module {
        single { ItemActionCreator(get()) }
    }
}

val dispatcherModule: Module by lazy {
    module {
        single<Dispatcher> { PublishSubject.create<Action>() }
    }
}

val storeModule: Module by lazy {
    module {
        single { ItemStore() }
    }
}

val viewModelModule: Module by lazy {
    module {
        single { MainViewModel(get(), get()) }
    }
}