package com.example.akihiro.fluxsample.domain.repository

import com.example.akihiro.fluxsample.domain.entity.Item
import com.example.akihiro.fluxsample.infra.API
import io.reactivex.Single

class ItemRepositoryImpl(private val api: API) : ItemRepository {
    override fun getItems(page: Int, perPage: Int, query: String?): Single<List<Item>> {
        return api.getItems(page, perPage, query)
    }
}