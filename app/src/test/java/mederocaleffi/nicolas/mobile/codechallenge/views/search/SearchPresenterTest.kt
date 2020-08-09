package mederocaleffi.nicolas.mobile.codechallenge.views.search

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList


class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter
    private lateinit var view: SearchView

    @Before
    fun setUp() {
        view = mock(SearchView::class.java)
        presenter = SearchPresenter(view)
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