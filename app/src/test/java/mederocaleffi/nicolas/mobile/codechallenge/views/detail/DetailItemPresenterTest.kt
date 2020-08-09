package mederocaleffi.nicolas.mobile.codechallenge.views.detail

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.repositories.DetailItemRepository

class DetailItemPresenterTest {

    private lateinit var presenter: DetailItemPresenter
    private lateinit var repository: DetailItemRepository
    private lateinit var view: DetailItemView


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        view = mock(DetailItemView::class.java)
        repository = mock(DetailItemRepository::class.java)
        presenter = DetailItemPresenter(view)
    }

    @Test
    fun `when item is null then show loading data error`(){
        presenter.setItemData(null)

        verify(view, times(1)).showLoadingItemDataError()
    }

    @Test
    fun `when item is not null and is liked then set like in view`() {
        val item = ItemList("1234", true)

        presenter.setItemData(item)

        verify(view, times(1)).setLikeStatus()
    }

    @Test
    fun `when item is not null and is not liked then don't set like in view`() {
        val item = ItemList("1234", false)

        presenter.setItemData(item)

        verify(view, times(0)).setLikeStatus()
    }

    @Test
    fun `when users like an item then update the view`() {
        presenter.onLikeButtonClicked(false)

        verify(view, times(1)).onLikedItem()
    }

    @Test
    fun `when users unlike an item then update the view`() {
        presenter.onLikeButtonClicked(true)

        verify(view, times(1)).onUnlikedItem()
    }

    @Test
    fun `when users click on share button then update the view with bottom sheet`(){
        presenter.onShareButtonClicked()

        verify(view, times(1)).showShareBottomSheet()
    }

    @Test
    fun `when rotate the device restore unliked state`() {
        presenter.restoreLikedState(false)

        verify(view, times(1)).setNotLikeStatus()
    }

    @Test
    fun `when rotate the device restore liked state`() {
        presenter.restoreLikedState(true)

        verify(view, times(1)).setLikeStatus()
    }

    @Test
    fun `when user clicks on moreProductInfoButton then view show message`() {
        presenter.onMoreProductInfoButtonClicked()

        verify(view, times(1)).showMoreProductInfoMessage()
    }
}