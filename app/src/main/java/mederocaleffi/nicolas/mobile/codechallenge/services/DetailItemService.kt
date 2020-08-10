package mederocaleffi.nicolas.mobile.codechallenge.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import mederocaleffi.nicolas.mobile.codechallenge.models.Seller

/**
 * Service for getting detail info of an item and it's seller
 */
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