package utn.frba.mobile.codechallenge.repositories.impl

import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import utn.frba.mobile.codechallenge.models.SearchModel
import utn.frba.mobile.codechallenge.repositories.SearchRepository
import utn.frba.mobile.codechallenge.services.SearchService

class DefaultSearchRepository : SearchRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun searchByName(
        query: String,
        onSuccess: (SearchModel) -> Unit?,
        onFailure: () -> Unit?
    ) {
        val service = retrofit.create(SearchService::class.java)
        val call = service.getItemsListByQuery(query)

        call.enqueue(object: Callback<SearchModel>{
            override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                if (response.code() == OK_HTTP && response.body() != null) {
                    val model = response.body() as SearchModel
                    onSuccess.invoke(model)
                }
            }
            override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                return
            }
        })
    }

    companion object{
        private const val BASE_URL = "https://api.mercadolibre.com/sites/MLA/"
        private const val OK_HTTP = 200
    }
}