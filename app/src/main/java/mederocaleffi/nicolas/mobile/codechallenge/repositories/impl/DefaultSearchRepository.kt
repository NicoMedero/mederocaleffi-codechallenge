package mederocaleffi.nicolas.mobile.codechallenge.repositories.impl

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import mederocaleffi.nicolas.mobile.codechallenge.models.SearchModel
import mederocaleffi.nicolas.mobile.codechallenge.repositories.SearchRepository
import mederocaleffi.nicolas.mobile.codechallenge.services.SearchService
import mederocaleffi.nicolas.mobile.codechallenge.views.search.SearchPresenterInterface

/**
 * This repository implements the call for search items by name.
 * The result is a SearchModel with paging and a list of items.
 */
class DefaultSearchRepository : BaseRepository(), SearchRepository {

    override fun searchByName(
        query: String,
        offset: Int?,
        presenter: SearchPresenterInterface
    ) {
        val service = retrofit.create(SearchService::class.java)
        val call = service.getItemsListByQuery(query, offset)

        call.enqueue(object: Callback<SearchModel>{
            override fun onResponse(call: Call<SearchModel>, response: Response<SearchModel>) {
                if (response.code() == OK_HTTP && response.body() != null) {
                    val model = response.body() as SearchModel
                    onSuccessByOffset(model, offset, presenter)
                } else {
                    onFailureByOffset(offset, presenter)
                }
            }
            override fun onFailure(call: Call<SearchModel>, t: Throwable) {
                onFailureByOffset(offset, presenter)
            }
        })
    }

    private fun onSuccessByOffset(model: SearchModel, offset: Int?, presenter: SearchPresenterInterface) {
        if (offset == 0 || offset == null) {
            presenter.onSuccessQuery(model)
        } else {
            presenter.onSuccessGetMoreItems(model)
        }
    }

    private fun onFailureByOffset(offset: Int?, presenter: SearchPresenterInterface) {
        if (offset == 0 || offset == null) {
            presenter.onFailureQuery()
        } else {
            presenter.onFailureGetMoreItems()
        }
    }
}