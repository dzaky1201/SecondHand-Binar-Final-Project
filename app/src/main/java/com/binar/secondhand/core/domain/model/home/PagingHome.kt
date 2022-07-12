package com.binar.secondhand.core.domain.model.home

data class PagingHome<T>(
    val `data`: List<T>? = emptyList(),
    val page: Int?,
    val perPage: Int?
)