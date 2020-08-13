package mederocaleffi.nicolas.mobile.codechallenge.views.search

import mederocaleffi.nicolas.mobile.codechallenge.factory.SearchModelFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.repositories.SearchRepository
import org.mockito.ArgumentMatchers.anyList


class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter
    private lateinit var repository: SearchRepository
    private lateinit var view: SearchView
    private val searchFactory = SearchModelFactory()

    @Before
    fun setUp() {
        view = mock(SearchView::class.java)
        repository = mock(SearchRepository::class.java)
        presenter = SearchPresenter(view, repository)
    }

    @Test
    fun `when success repository call but null response then showEmptyResult`() {
        val emptySearch = searchFactory.emptySearchModel()

        doAnswer { invocation ->
            invocation.getArgument<SearchPresenterInterface>(2).onSuccessQuery(emptySearch)
        }.`when`(repository).searchByName("", null, presenter)

        presenter.onQuerySubmit("")

        verify(view, times(1)).emptyResultsFromQuery()
        verify(view, times(0)).stopProgressBar()
    }

    @Test
    fun `when success repository call but not null response then loadResults`() {
        val searchResult = searchFactory.searchWithData()

        doAnswer { invocation ->
            invocation.getArgument<SearchPresenterInterface>(2).onSuccessQuery(searchResult)
        }.`when`(repository).searchByName("", null, presenter)

        presenter.onQuerySubmit("")

        verify(view, times(0)).emptyResultsFromQuery()
        verify(view, times(1)).loadQueryResults(searchResult.results)
        verify(view, times(1)).stopProgressBar()
    }

    @Test
    fun `when failure repository call then update the view`() {
        doAnswer { invocation ->
            invocation.getArgument<SearchPresenterInterface>(2).onFailureQuery()
        }.`when`(repository).searchByName("", null, presenter)

        presenter.onQuerySubmit("")

        verify(view, times(1)).stopProgressBar()
        verify(view, times(1)).onFailureQuery()
    }

    @Test
    fun `when get more items success but empty results then show no more items`() {
        val emptySearch = searchFactory.emptySearchModel()

        doAnswer { invocation ->
            invocation.getArgument<SearchPresenterInterface>(2).onSuccessGetMoreItems(emptySearch)
        }.`when`(repository).searchByName("", 0, presenter)

        presenter.getMoreItems(0,"")

        verify(view, times(1)).noMoreItemsToShow()
        verify(view, times(0)).addItemsAtTheEnd(anyList())
    }

    @Test
    fun `when get more items success and not empty results then update the view`() {
        val search = searchFactory.searchWithData()

        doAnswer { invocation ->
            invocation.getArgument<SearchPresenterInterface>(2).onSuccessGetMoreItems(search)
        }.`when`(repository).searchByName("", 0, presenter)

        presenter.getMoreItems(0,"")

        verify(view, times(0)).noMoreItemsToShow()
        verify(view, times(1)).addItemsAtTheEnd(search.results)
    }

    @Test
    fun `when get more items fail then update the view`() {
        doAnswer { invocation ->
            invocation.getArgument<SearchPresenterInterface>(2).onFailureGetMoreItems()
        }.`when`(repository).searchByName("", 0, presenter)

        presenter.getMoreItems(0, "")

        verify(view, times(1)).onFailureGettingMoreItems()
    }

    @Test
    fun `when submit query show ProgressBar and Clear previous data`() {
        presenter.onQuerySubmit("query")

        verify(view, times(1)).showProgressBar()
        verify(view, times(1)).clearData()
    }

    @Test
    fun `when query retrieved in extras then execute query search`() {
        val mockItemsList = ArrayList<ItemList>()
        val mockItemList = ItemList("", false)

        presenter.onExtrasRetrieved("query", mockItemList, mockItemsList)

        verify(view, times(1)).setQueryInSearchView("query")
    }

    @Test
    fun `when null query and not null item then show and stop progress bar`() {
        val mockItemsList = ArrayList<ItemList>()
        val mockItemList = ItemList("", false)

        presenter.onExtrasRetrieved(null, mockItemList, mockItemsList)

        verify(view, times(1)).showProgressBar()
        verify(view, times(1)).stopProgressBar()
    }

    @Test
    fun `when null query and not null item with liked status then update in itemsList`(){
        val itemsList = mockedItemsList()
        val retrievedLikedItem = ItemList("2345", true)

        presenter.onExtrasRetrieved(null, retrievedLikedItem, itemsList)

        verify(view, times(1)).updateItemsListWithItemLiked(itemsList)
    }

    @Test
    fun `when null query and not null item but with same like state then dont update view`() {
        val itemsList = mockedItemsList()
        val retrievedLikedItem = ItemList("2345", false)

        presenter.onExtrasRetrieved(null, retrievedLikedItem, itemsList)

        verify(view, times(0)).updateItemsListWithItemLiked(itemsList)
    }

    private fun mockedItemsList(): List<ItemList> {
        return listOf(
            ItemList(
                "1234",
                false
            ),
            ItemList(
                "2345",
                false
            ),
            ItemList(
                "3456",
                true
            )
        )
    }

}