package utn.frba.mobile.codechallenge.views.search

import utn.frba.mobile.codechallenge.models.ItemList

interface SearchView {

    fun showProgressBar()

    fun loadQueryResults(results: List<ItemList>)

    fun stopProgressBar()

    fun addItemsAtTheEnd(results: List<ItemList>)
}