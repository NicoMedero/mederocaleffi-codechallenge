package utn.frba.mobile.codechallenge.views.search

import utn.frba.mobile.codechallenge.models.SearchModel
import utn.frba.mobile.codechallenge.repositories.SearchRepository
import utn.frba.mobile.codechallenge.repositories.impl.DefaultSearchRepository

class SearchPresenter(private val view: SearchView) {

    private val repository: SearchRepository = DefaultSearchRepository()
    private var query: String? = null

    fun onQuerySubmit(query: String) {
        view.showProgressBar()
        this.query = query
        repository.searchByName(query, null, { onSuccessQuery(it) }, { onFailureQuery() })
    }

    private fun onSuccessQuery(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            //TODO: display non results from query
            return
        }

        view.loadQueryResults(searchModel.results)
        view.stopProgressBar()
    }

    private fun onFailureQuery() {

    }

    fun getMoreItems(offset: Int) {
        repository.searchByName(query!!, offset, { onSuccessGetMoreItems(it) }, {})
    }

    private fun onSuccessGetMoreItems(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            //TODO: there is no more items :(
            return
        }

        view.addItemsAtTheEnd(searchModel.results)
    }
}