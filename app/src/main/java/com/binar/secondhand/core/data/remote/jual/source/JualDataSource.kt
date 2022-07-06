package com.binar.secondhand.core.data.remote.jual.source

import android.util.Log
import com.binar.secondhand.core.data.remote.jual.JualService
import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.utils.JualMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class JualDataSource(private val jualService: JualService) {

    private val idCategory: ArrayList<RequestBody> = arrayListOf()

    fun postProduct(sellerProductRequest: SellerProductRequest, fileImage: File): Observable<SellerProduct> {
        val file = convertFile(fileImage)
        val map = HashMap<String, RequestBody>().apply {
            put("name", sellerProductRequest.name!!.toRequestBody("text/plain".toMediaTypeOrNull()))
            put(
                "base_price",
                sellerProductRequest.basePrice!!.toRequestBody("text/plain".toMediaTypeOrNull())
            )
            put(
                "description",
                sellerProductRequest.description!!.toRequestBody("text/plain".toMediaTypeOrNull())
            )
            put(
                "location",
                sellerProductRequest.location!!.toRequestBody("text/plain".toMediaTypeOrNull())
            )

        }
        for (category in sellerProductRequest.categorId) {
            Log.d("cekId", category)
            idCategory.add(category.toRequestBody("text/plain".toMediaTypeOrNull()))
        }

        return jualService.postProduct(map, idCategory, file).mapObservable {
            JualMapper.mapSellerProductResponseToEntity(it)
        }
    }

    private fun convertFile(file: File): MultipartBody.Part {
        val reqFile: RequestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }
}