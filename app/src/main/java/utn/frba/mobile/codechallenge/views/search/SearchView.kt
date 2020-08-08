package utn.frba.mobile.codechallenge.views.search

import utn.frba.mobile.codechallenge.models.ItemList

interface SearchView {

    fun showProgressBar()

    fun loadQueryResults(results: List<ItemList>)

    fun stopProgressBar()

    fun addItemsAtTheEnd(results: List<ItemList>)

    fun clearData()

    fun onFailureQuery()

    fun emptyResultsFromQuery()

    fun onFailureGettingMoreItems()

    fun noMoreItemsToShow()

    fun updateViewFromExtras(query: String?, item: ItemList?)

    fun updateItemsListWithItemLiked(itemsList: List<ItemList>)

    fun setQueryInSearchView(query: String)
}