package com.example.akihiro.fluxsample.domain.repository

import com.example.akihiro.fluxsample.domain.entity.Item
import io.reactivex.Single

interface ItemRepository {

    fun getItems(page: Int, perPage: Int, query: String?): Single<List<Item>>
}