package com.example.akihiro.fluxsample

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("")
    fun getItems(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String
    ): Single<List<Item>>
}