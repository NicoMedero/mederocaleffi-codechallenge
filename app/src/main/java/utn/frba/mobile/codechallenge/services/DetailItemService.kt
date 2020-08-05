package utn.frba.mobile.codechallenge.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import utn.frba.mobile.codechallenge.models.DetailItem

interface DetailItemService {

    @GET("items/{id}")
    fun getItemsById(
        @Path("id") id: String
    ): Call<DetailItem>
}