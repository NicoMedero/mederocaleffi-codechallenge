package utn.frba.mobile.codechallenge.views.search

import utn.frba.mobile.codechallenge.models.SearchModel
import utn.frba.mobile.codechallenge.repositories.SearchRepository
import utn.frba.mobile.codechallenge.repositories.impl.DefaultSearchRepository

class SearchPresenter(private val view: SearchView) {

    private val repository: SearchRepository = DefaultSearchRepository()
    private var query: String? = null

    fun onQuerySubmit(query: String) {
        view.showProgressBar()
        view.clearData()
        this.query = query
        repository.searchByName(query, null, { onSuccessQuery(it) }, { onFailureQuery() })
    }

    private fun onSuccessQuery(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            view.emptyResultsFromQuery()
            return
        }

        view.loadQueryResults(searchModel.results)
        view.stopProgressBar()
    }

    private fun onFailureQuery() {
        view.stopProgressBar()
        view.onFailureQuery()
    }

    fun getMoreItems(offset: Int) {
        repository.searchByName(query!!, offset, { onSuccessGetMoreItems(it) }, { view.onFailureGettingMoreItems() })
    }

    private fun onSuccessGetMoreItems(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            view.noMoreItemsToShow()
            return
        }

        view.addItemsAtTheEnd(searchModel.results)
    }
}