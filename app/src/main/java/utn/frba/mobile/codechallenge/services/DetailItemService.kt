package utn.frba.mobile.codechallenge.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.Seller

interface DetailItemService {

    @GET("items/{id}")
    fun getItemsById(
        @Path("id") id: String
    ): Call<DetailItem>

    @GET("users/{id}")
    fun getSellerById(
        @Path("id") id: Int
    ): Call<Seller>
}