package com.example.akihiro.fluxsample.domain.repository

import com.example.akihiro.fluxsample.domain.entity.Item
import com.example.akihiro.fluxsample.infra.RetrofitClient
import io.reactivex.Single

class ItemRepositoryImpl(private val client: RetrofitClient) :
    ItemRepository {

    override fun getItems(page: Int, perPage: Int, query: String?): Single<List<Item>> {
        return client.api.getItems(page, perPage, query)
    }
}