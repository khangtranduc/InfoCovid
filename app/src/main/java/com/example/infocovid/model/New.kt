package com.example.infocovid.model

import kotlinx.serialization.Serializable

data class New(
    val news: List<New>
) {
    @Serializable
    data class New(
        val content: String,
        val imageFileName: String?,
        val imageInLocalStorage: String,
        val link: String,
        val news_id: String,
        val pubDate: String,
        val reference: String,
        val title: String,
        val urlToImage: String
    )
}