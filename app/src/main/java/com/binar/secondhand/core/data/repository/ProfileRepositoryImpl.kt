package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.profile.source.ProfileDataSource
import com.binar.secondhand.core.data.local.DataPreferences
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.repository.IProfileRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly
import java.io.File

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource) : IProfileRepository {
    private val compositeDisposable = CompositeDisposable()

    private var _loginStateEventManager: MutableStateEventManager<Login> =
        MutableStateEventManager()
    override val loginStateEventManager: StateEventManager<Login>
        get() = _loginStateEventManager


    private var _userStateEventManager: MutableStateEventManager<User> = MutableStateEventManager()
    override val userStateEventManager: StateEventManager<User>
        get() = _userStateEventManager

    private var _updateUserStateEventManager: MutableStateEventManager<User> =
        MutableStateEventManager()
    override val updateUserStateEventManager: StateEventManager<User>
        get() = _updateUserStateEventManager

    override fun login(request: LoginRequest) {
        val disposable = dataSource.postLogin(request).fetchStateEventSubscriber { stateEvent ->
            _loginStateEventManager.post(stateEvent)
        }

        compositeDisposable.add(disposable)
    }

    override fun updateUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        image: File
    ) {
        val disposable =
            dataSource.updateUser(fullname, email, password, address, phoneNumber, image)
                .fetchStateEventSubscriber {
                    _updateUserStateEventManager.post(it)
                }

        compositeDisposable.add(disposable)
    }

    override fun clearSession() {
        DataPreferences.get.clearSession()
    }

    override fun getUser() {
        println("get user.......")
        val disposable = dataSource.getUser().fetchStateEventSubscriber { stateEvent ->
            _userStateEventManager.post(stateEvent)
        }

        compositeDisposable.add(disposable)
    }

    override fun saveToken(token: String) {
        DataPreferences.get.token = token
    }

    override fun close() {
        _loginStateEventManager.closeQuietly()
        _userStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }

}