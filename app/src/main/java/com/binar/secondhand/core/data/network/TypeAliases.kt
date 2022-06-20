package com.binar.secondhand.core.data.network

import io.reactivex.Observable
import retrofit2.Response

typealias SecondHandResponse<T> = Observable<Response<T>>