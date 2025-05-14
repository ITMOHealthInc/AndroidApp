package ru.itmo.se.mad.ui.main.products.api.service

import retrofit2.Call
import ru.itmo.se.mad.ui.main.products.api.model.ProductApiModel
import ru.itmo.se.mad.ui.main.products.api.repository.ProductApi

class ProductService : ProductApi {
    override fun getProducts(): Call<List<ProductApiModel>> {
        TODO("Not yet implemented")
    }

    override fun addProduct(): Call<ProductApiModel> {
        TODO("Not yet implemented")
    }

    override fun getProductById(id: Int?): Call<ProductApiModel> {
        TODO("Not yet implemented")
    }

    override fun updateProductById(id: Int?): Call<ProductApiModel> {
        TODO("Not yet implemented")
    }

    override fun deleteProductById(id: Int?): Call<Void> {
        TODO("Not yet implemented")
    }
}