package com.binar.secondhand.core.data.network

import com.binar.secondhand.core.data.remote.home.response.paging.BasePagingResponse
import io.reactivex.Observable
import retrofit2.Response

typealias SecondHandResponse<T> = Observable<Response<T>>
typealias SecondHandPagingResponse<T> = Observable<Response<BasePagingResponse<T>>>