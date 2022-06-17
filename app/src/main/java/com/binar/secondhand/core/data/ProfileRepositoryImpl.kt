package com.binar.secondhand.core.data

import com.binar.secondhand.core.data.source.ProfileDataSource
import com.binar.secondhand.core.data.source.local.DataPreferences
import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.domain.repository.IProfileRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource): IProfileRepository {
    private val compositeDisposable = CompositeDisposable()

    private var _loginStateEventManager: MutableStateEventManager<Login> = MutableStateEventManager()
    override val loginStateEventManager: StateEventManager<Login>
        get() = _loginStateEventManager


    private var _userStateEventManager: MutableStateEventManager<User> = MutableStateEventManager()
    override val userStateEventManager: StateEventManager<User>
        get() = _userStateEventManager

    override fun login(request: LoginRequest) {
        val disposable = dataSource.postLogin(request).fetchStateEventSubscriber { stateEvent ->
            _loginStateEventManager.post(stateEvent)
        }

        compositeDisposable.add(disposable)
    }

    override fun getUser() {
        val disposable = dataSource.getUser().fetchStateEventSubscriber { stateEvent ->
            _userStateEventManager.post(stateEvent)
        }
    }

    override fun saveToken(token: String) {
        DataPreferences.get.token = token
    }

    override fun close() {
        _loginStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }

}