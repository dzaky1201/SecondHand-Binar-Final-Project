package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.event.StateEvent
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response

// interactor mapper
// original ->  Observable<Response<BaseResponse<T>>>
// use higher order function for mapper data from response to entity
fun <T: Any, U: Any> SecondHandResponse<T>.mapObservable(mapper: (T) -> U): Observable<U> {
    return flatMap { response ->
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val dataMapper = mapper.invoke(body)
                Observable.just(dataMapper)
            } else {
                val exception = Throwable("Response data is null, message: error")
                Observable.error(exception)
            }
        } else {
            val bodyError = response.errorBody()?.string()
            val gson = GsonBuilder()
                .setPrettyPrinting()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val typeToken = object : TypeToken<Response<Any>>() {}.type
            val bodyErrorData = gson.fromJson<Response<Any>>(bodyError, typeToken)
            println("base response data -> $bodyErrorData")
            val messageHttp = response.message()
            val message = "$messageHttp, message: $bodyErrorData"
            val exception = HttpException(response)
            Observable.error(exception)
        }
    }
}

// fetcher state event
// merubah data observable entity ke dalam subscriber state event
fun <T: Any> Observable<T>.fetchStateEventSubscriber(onSubscribe: (StateEvent<T>) -> Unit) : Disposable {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            val eventLoading = StateEvent.Loading<T>()
            onSubscribe.invoke(eventLoading)
        }
        .subscribe({ data ->
            val eventSuccess = StateEvent.Success<T>(data)
            onSubscribe.invoke(eventSuccess)
        }, { throwable ->
            val code = if (throwable is HttpException) {
                throwable.code()
            } else {
                499
            }
            val eventFailure = StateEvent.Failure<T>(code, throwable)
            onSubscribe.invoke(eventFailure)
        })
}