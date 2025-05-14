package ru.itmo.se.mad.ui.main.products.api.repository

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.itmo.se.mad.ui.main.products.api.model.ProductApiModel

interface ProductApi {
    @GET("/products")
    fun getProducts(): Call<List<ProductApiModel>>

    @POST("/products")
    fun addProduct(): Call<ProductApiModel>

    @GET("/products/{id}")
    fun getProductById(id: Int?) : Call<ProductApiModel>

    @PUT("/products/{id}")
    fun updateProductById(id: Int?) : Call<ProductApiModel>

    @DELETE("products/{id}")
    fun deleteProductById(id: Int?) : Call<Void>
}