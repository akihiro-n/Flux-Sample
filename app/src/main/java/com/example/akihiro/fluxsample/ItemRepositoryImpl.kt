package com.example.akihiro.fluxsample

import io.reactivex.Single

class ItemRepositoryImpl(private val client: RetrofitClient) : ItemRepository {

    override fun getItems(page: Int, perPage: Int, query: String?): Single<List<Item>> {
        return client.api.getItems(page, perPage, query)
    }
}