package com.example.akihiro.fluxsample.domain.entity

data class User (
    val id: String,
    val name: String,
    val itemsCount: Int?,
    val profileImageUrl: String
)