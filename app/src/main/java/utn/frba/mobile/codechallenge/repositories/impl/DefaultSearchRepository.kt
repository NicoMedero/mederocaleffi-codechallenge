package utn.frba.mobile.codechallenge.repositories.impl

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utn.frba.mobile.codechallenge.models.SearchModel
import utn.frba.mobile.codechallenge.repositories.SearchRepository
import utn.frba.mobile.codechallenge.services.SearchService

class DefaultSearchRepository : BaseRepository(), SearchRepository {

    override fun searchByName(
        query: String,
        offset: Int?,
        onSuccess: (SearchModel) -> Unit?,
        onFailure: () -> Unit?
    ) {
        val service = retrofit.create(SearchService::class.java)
        val call = service.getItemsListByQuery(query, offset)

        call.enqueue(object: Callback<SearchModel>{
            override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                if (response.code() == OK_HTTP && response.body() != null) {
                    val model = response.body() as SearchModel
                    onSuccess.invoke(model)
                }
            }
            override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }
}