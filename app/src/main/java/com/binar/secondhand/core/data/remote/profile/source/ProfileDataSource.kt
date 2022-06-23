package com.binar.secondhand.core.data.remote.profile.source

import com.binar.secondhand.core.data.remote.profile.AuthService
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.utils.ProfileMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProfileDataSource(private val authService: AuthService) {

    fun postLogin(request: LoginRequest): Observable<Login> {
        return authService.postLogin(request).mapObservable {
            ProfileMapper.mapLoginResponseToEntity(it)
        }
    }

    fun getUser(): Observable<User> {
        println("get user data sources...")
        return authService.getUser().mapObservable {
            println("mapper....")
            ProfileMapper.mapUserResponseToEntity(it)
        }
    }

    fun updateUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String
    ): Observable<User> {
        val fullnameString = convertToString(fullname)
        val emailString = convertToString(email)
        val passwordString = convertToString(password)
        val addressString = convertToString(address)
        val cityString = convertToString(city)
        val phoneNumberString = convertToString(phoneNumber)
        return authService.updateUser(
            fullnameString,
            emailString,
            passwordString,
            phoneNumberString,
            addressString,
            cityString
        ).mapObservable {
            ProfileMapper.mapUserResponseToEntity(
                it
            )
        }
    }

    fun updateUserWithImage(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String,
        file: File
    ): Observable<User> {
        val fullnameString = convertToString(fullname)
        val emailString = convertToString(email)
        val passwordString = convertToString(password)
        val addressString = convertToString(address)
        val phoneNumberString = convertToString(phoneNumber)
        val cityString = convertToString(city)
        val photo = convertFile(file)
        return authService.updateUserWithImage(
            fullnameString,
            emailString,
            passwordString,
            phoneNumberString,
            addressString,
            cityString,
            photo
        ).mapObservable {
            ProfileMapper.mapUserResponseToEntity(
                it
            )
        }
    }

    fun registerUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String,
        file: File
    ): Observable<User> {
        val fullnameString = convertToString(fullname)
        val emailString = convertToString(email)
        val passwordString = convertToString(password)
        val addressString = convertToString(address)
        val phoneNumberString = convertToString(phoneNumber)
        val cityString = convertToString(city)
        val photo = convertFile(file)
        return authService.registerUser(
            fullnameString,
            emailString,
            passwordString,
            phoneNumberString,
            addressString,
            cityString,
            photo
        ).mapObservable {
            ProfileMapper.mapUserResponseToEntity(
                it
            )
        }
    }

    private fun convertToString(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun convertFile(file: File): MultipartBody.Part {
        val reqFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }
}