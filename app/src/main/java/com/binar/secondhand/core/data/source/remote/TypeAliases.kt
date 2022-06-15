package com.binar.secondhand.core.data.source.remote

import io.reactivex.Observable
import retrofit2.Response

typealias SecondHandResponse<T> = Observable<Response<T>>