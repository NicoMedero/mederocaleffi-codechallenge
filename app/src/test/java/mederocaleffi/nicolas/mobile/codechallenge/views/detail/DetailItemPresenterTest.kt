package mederocaleffi.nicolas.mobile.codechallenge.views.detail

import mederocaleffi.nicolas.mobile.codechallenge.factory.DetailItemFactory
import mederocaleffi.nicolas.mobile.codechallenge.factory.ItemListFactory
import mederocaleffi.nicolas.mobile.codechallenge.factory.SellerFactory
import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.models.Seller
import mederocaleffi.nicolas.mobile.codechallenge.repositories.DetailItemRepository
import org.junit.Assert.*
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.anyInt

class DetailItemPresenterTest {

    private val detailItemFactory = DetailItemFactory()
    private val itemListFactory = ItemListFactory()
    private val sellerFactory = SellerFactory()
    private lateinit var presenter: DetailItemPresenter
    private lateinit var repository: DetailItemRepository
    private lateinit var view: DetailItemView


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        view = mock(DetailItemView::class.java)
        repository = mock(DetailItemRepository::class.java)
        presenter = DetailItemPresenter(view, repository)
    }

    @Test
    fun `when repository call then execute onSuccess with complete DetailItem`() {

        val completeDetailItem = detailItemFactory.detailItemWithQuantityAndAttr()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(completeDetailItem)
        }.`when`(repository).searchItemById("", presenter)

        presenter.setItemData(itemListFactory.itemListEmpty())

        verify(view, times(1)).stopProgressBar()
        verify(view, times(1)).setMainItemDetails(completeDetailItem)
        verify(view, times(1)).setAvailableStock(anyInt())
        verify(view, times(1)).setProductInfoDetails(ArrayList())
        verify(view, times(0)).showUnavailableStock()
        verify(view, times(0)).hideProductInfoDetails()
    }

    @Test
    fun `when repository call then execute onSuccess without Quantity And Attr`() {

        val emptyDetailItem = detailItemFactory.detailItemEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(emptyDetailItem)
        }.`when`(repository).searchItemById("", presenter)

        presenter.setItemData(itemListFactory.itemListEmpty())

        verify(view, times(1)).stopProgressBar()
        verify(view, times(1)).setMainItemDetails(emptyDetailItem)
        verify(view, times(1)).showUnavailableStock()
        verify(view, times(1)).hideProductInfoDetails()
    }

    @Test
    fun `when repository call then execute onSuccess with Seller data`() {

        val seller = sellerFactory.sellerWithData()
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(seller)
        }.`when`(repository).searchSellerById(0, presenter)

        presenter.setItemData(emptyItem)

        verify(view, times(1)).setSellerName(anyString())
        verify(view, times(1)).setSellerReputationAndQuantitySold(anyString(), anyInt())
        verify(view, times(0)).setSellerWithoutReputation()
    }

    @Test
    fun `when repository call then execute onSuccess with empty seller data`() {
        val emptySeller = sellerFactory.sellerEmptyData()
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(emptySeller)
        }.`when`(repository).searchSellerById(0, presenter)

        presenter.setItemData(emptyItem)

        verify(view, times(1)).setSellerWithoutReputation()
        verify(view, times(0)).setSellerName(anyString())
        verify(view, times(0)).setSellerReputationAndQuantitySold(anyString(), anyInt())
    }

    @Test
    fun `when onSuccess repository calls then cache detailItem and Seller`() {
        val detailItem = detailItemFactory.detailItemWithId("1234")
        val seller = sellerFactory.sellerWithData()
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(detailItem)
        }.`when`(repository).searchItemById("", presenter)

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(seller)
        }.`when`(repository).searchSellerById(0, presenter)

        presenter.setItemData(emptyItem)
        presenter.setItemData(emptyItem)

        verify(repository, times(1)).searchItemById("", presenter)
        verify(repository, times(1)).searchSellerById(0, presenter)
    }

    @Test
    fun `when repository call is onFailure then update the view`() {
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onFailure()
        }.`when`(repository).searchItemById("", presenter)

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onFailureGettingSellerData()
        }.`when`(repository).searchSellerById(0, presenter)

        presenter.setItemData(emptyItem)

        verify(view, times(1)).showLoadingItemDataError()
        verify(view, times(1)).showGetSellerInfoError()
    }

    @Test
    fun `when stockSelector clicked and item has not null quantity then show bottom sheet`() {
        val detailItem = detailItemFactory.detailItemWithId("1234")
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(detailItem)
        }.`when`(repository).searchItemById("", presenter)

        presenter.setItemData(emptyItem)

        presenter.onStockSelectorClicked()

        verify(view, times(1)).showStockQuantityBottomSheetSelector(anyInt())
    }

    @Test
    fun `when stockSelector clicked and item has null quantity then show error`() {
        presenter.onStockSelectorClicked()

        verify(view, times(1)).showErrorForQuantitySelector()
    }

    @Test
    fun `when item is null then show loading data error`(){
        presenter.setItemData(null)

        verify(view, times(1)).showLoadingItemDataError()
    }

    @Test
    fun `when get item instance then return it correctly`(){
        val detailItem = detailItemFactory.detailItemWithId("1234")
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(detailItem)
        }.`when`(repository).searchItemById("", presenter)

        presenter.setItemData(emptyItem)

        val recoveredItem = presenter.getDetailItemInstance() as DetailItem

        assertEquals(recoveredItem.id, detailItem.id)
        assertEquals(recoveredItem.title, detailItem.title)
        assertEquals(recoveredItem.price, detailItem.price)
    }

    @Test
    fun `when get seller instance then return it correctly`() {
        val seller = sellerFactory.sellerWithData()
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(seller)
        }.`when`(repository).searchSellerById(0, presenter)

        presenter.setItemData(emptyItem)

        val recoveredSeller = presenter.getSellerDetailInstance() as Seller

        assertEquals(recoveredSeller.id, seller.id)
        assertEquals(recoveredSeller.nickname, seller.nickname)
        assertEquals(recoveredSeller.reputation, seller.reputation)
    }

    @Test
    fun `when restoring an item detail then get correctly`() {
        val detailItem = detailItemFactory.detailItemWithId("1234")

        presenter.restoreDetailItemState(detailItem)

        val recoveredItem = presenter.getDetailItemInstance() as DetailItem

        assertEquals(recoveredItem, detailItem)
        assertEquals(recoveredItem.id, detailItem.id)
    }

    @Test
    fun `when restoring seller details then get it correctly`() {
        val seller = sellerFactory.sellerWithData()

        presenter.restoreSellerDetailState(seller)

        val restoredSeller = presenter.getSellerDetailInstance() as Seller

        assertEquals(restoredSeller, seller)
        assertEquals(restoredSeller.nickname, seller.nickname)
    }

    @Test
    fun `when getting item state then return it correctly`() {
        val detailItem = detailItemFactory.detailItemWithQuantityAndAttr()
        val emptyItem = itemListFactory.itemListEmpty()

        doAnswer {invocation ->
            invocation.getArgument<DetailItemPresenterInterface>(1).onSuccess(detailItem)
        }.`when`(repository).searchItemById("", presenter)

        presenter.setItemData(emptyItem)

        val recoveredItemLiked = presenter.getItemState(true)
        val recoveredItemUnliked = presenter.getItemState(false)

        assertNotNull(recoveredItemLiked)
        assertNotNull(recoveredItemUnliked)
        assertEquals(recoveredItemLiked?.id, detailItem.id)
        assertEquals(recoveredItemUnliked?.id, detailItem.id)
        assertTrue(recoveredItemLiked!!.like)
        assertFalse(recoveredItemUnliked!!.like)
    }

    @Test
    fun `when getting item state and detailItem is null then return null`() {
        val recoveredItem = presenter.getItemState(false)
        assertNull(recoveredItem)
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