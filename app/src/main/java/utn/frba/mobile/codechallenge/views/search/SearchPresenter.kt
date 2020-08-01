package utn.frba.mobile.codechallenge.views.search

import utn.frba.mobile.codechallenge.repositories.SearchRepository
import utn.frba.mobile.codechallenge.repositories.impl.DefaultSearchRepository

class SearchPresenter(private val view: SearchView) {

    private val repository: SearchRepository = DefaultSearchRepository()

    fun onQuerySubmit(query: String?) {

    }
}