package mederocaleffi.nicolas.mobile.codechallenge.views.search

import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.models.SearchModel
import mederocaleffi.nicolas.mobile.codechallenge.repositories.SearchRepository
import mederocaleffi.nicolas.mobile.codechallenge.repositories.impl.DefaultSearchRepository

class SearchPresenter(
    private val view: SearchView,
    private val repository: SearchRepository
) : SearchPresenterInterface {

    fun onQuerySubmit(query: String) {
        view.showProgressBar()
        view.clearData()
        repository.searchByName(query, null, this)
    }

    override fun onSuccessQuery(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            view.emptyResultsFromQuery()
            return
        }

        view.loadQueryResults(searchModel.results)
        view.stopProgressBar()
    }

    override fun onFailureQuery() {
        view.stopProgressBar()
        view.onFailureQuery()
    }

    fun getMoreItems(offset: Int, query: String) {
        repository.searchByName(query, offset, this)
    }

    override fun onSuccessGetMoreItems(searchModel: SearchModel) {
        if (searchModel.results.isNullOrEmpty()) {
            view.noMoreItemsToShow()
            return
        }

        view.addItemsAtTheEnd(searchModel.results)
    }

    override fun onFailureGetMoreItems() {
        view.onFailureGettingMoreItems()
    }

    /**
     * This method is used to update the search view when the user comes from detail view.
     *
     * @param query, if it's not null, the presenter must execute the search as usual and nothing else.
     * @param receivedItem, if it's not null, the presenter must to analyze if the liked state is different
     *      from the like state of the item inside itemsList
     * @param itemsList, is the actual list from the search view. The presenter must use it to find
     *      if there's any change between the Item received from the detail view.
     */
    fun onExtrasRetrieved(query: String?, receivedItem: ItemList?, itemsList: List<ItemList>) {
        if (!query.isNullOrEmpty()) {
            onQuerySubmit(query)
            view.setQueryInSearchView(query)
            return
        }

        if (receivedItem != null) {
            view.showProgressBar()

            val itemsListToUpdate = itemsList.toMutableList()

            val itemToUpdate = itemsListToUpdate.find { itemList -> itemList.id == receivedItem.id }

            if (itemToUpdate != null && itemToUpdate.like != receivedItem.like) {
                val itemToUpdateIndex = itemsListToUpdate.indexOf(itemToUpdate)

                itemToUpdate.like = !itemToUpdate.like
                itemsListToUpdate[itemToUpdateIndex] = itemToUpdate

                view.updateItemsListWithItemLiked(itemsListToUpdate.toList())
            }

            view.stopProgressBar()
        }
    }
}

interface SearchPresenterInterface {
    fun onSuccessQuery(searchModel: SearchModel)

    fun onSuccessGetMoreItems(searchModel: SearchModel)

    fun onFailureQuery()

    fun onFailureGetMoreItems()
}