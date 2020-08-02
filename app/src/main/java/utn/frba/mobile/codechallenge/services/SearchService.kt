package utn.frba.mobile.codechallenge.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import utn.frba.mobile.codechallenge.models.SearchModel

interface SearchService {

    @GET("search")
    fun getItemsListByQuery(
        @Query("q") q: String,
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): Call<SearchModel>
}