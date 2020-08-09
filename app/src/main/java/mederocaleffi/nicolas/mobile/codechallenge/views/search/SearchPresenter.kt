package mederocaleffi.nicolas.mobile.codechallenge.views.search

import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.models.SearchModel
import mederocaleffi.nicolas.mobile.codechallenge.repositories.SearchRepository
import mederocaleffi.nicolas.mobile.codechallenge.repositories.impl.DefaultSearchRepository

class SearchPresenter(private val view: SearchView) {

    private val repository: SearchRepository = DefaultSearchRepository()

    fun onQuerySubmit(query: String) {
        view.showProgressBar()
        view.clearData()
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

    fun getMoreItems(offset: Int, query: String) {
        repository.searchByName(query, offset, { onSuccessGetMoreItems(it) }, { view.onFailureGettingMoreItems() })
    }

    private fun onSuccessGetMoreItems(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            view.noMoreItemsToShow()
            return
        }

        view.addItemsAtTheEnd(searchModel.results)
    }

    fun onExtrasRetrieved(query: String?, item: ItemList?, itemsList: List<ItemList>) {
        if (!query.isNullOrEmpty()) {
            onQuerySubmit(query)
            view.setQueryInSearchView(query)
            return
        }

        if (item != null) {
            view.showProgressBar()

            val itemsListToUpdate = itemsList.toMutableList()

            val itemToUpdate = itemsListToUpdate.find { itemList -> itemList.id == item.id }

            if (itemToUpdate != null && itemToUpdate.like != item.like) {
                val itemIndex = itemsListToUpdate.indexOf(itemToUpdate)

                itemToUpdate.like = !itemToUpdate.like
                itemsListToUpdate[itemIndex] = itemToUpdate

                view.updateItemsListWithItemLiked(itemsListToUpdate.toList())
            }

            view.stopProgressBar()
        }
    }
}