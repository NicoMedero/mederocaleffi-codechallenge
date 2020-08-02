package utn.frba.mobile.codechallenge.views.search

import utn.frba.mobile.codechallenge.models.SearchModel
import utn.frba.mobile.codechallenge.repositories.SearchRepository
import utn.frba.mobile.codechallenge.repositories.impl.DefaultSearchRepository

class SearchPresenter(private val view: SearchView) {

    private val repository: SearchRepository = DefaultSearchRepository()

    fun onQuerySubmit(query: String) {
        view.showProgressBar()
        repository.searchByName(query, null, null, { onSuccessQuery(it) }, { onFailureQuery() })
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
}