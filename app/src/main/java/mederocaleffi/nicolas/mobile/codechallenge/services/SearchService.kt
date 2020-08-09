package mederocaleffi.nicolas.mobile.codechallenge.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import mederocaleffi.nicolas.mobile.codechallenge.models.SearchModel

interface SearchService {

    @GET("sites/MLA/search")
    fun getItemsListByQuery(
        @Query("q") q: String,
        @Query("offset") offset: Int?
    ): Call<SearchModel>
}