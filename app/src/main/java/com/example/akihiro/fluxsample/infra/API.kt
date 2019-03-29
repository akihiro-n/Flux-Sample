package com.example.akihiro.fluxsample.infra

import com.example.akihiro.fluxsample.domain.entity.Item
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("/api/v2/items")
    fun getItems(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String?
    ): Single<List<Item>>
}