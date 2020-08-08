package utn.frba.mobile.codechallenge.views.detail

import utn.frba.mobile.codechallenge.models.AttributesItems
import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemList

interface DetailItemView {
    fun getItemForResult(): ItemList?

    fun setLikeStatus()

    fun setNotLikeStatus()

    fun stopProgressBar()

    fun setMainItemDetails(detailItem: DetailItem)

    fun onUnlikedItem()

    fun onLikedItem()

    fun showShareBottomSheet()

    fun setSellerName(nickname: String)

    fun setSellerReputationAndQuantitySold(reputation: String, quantitySold: Int)

    fun showLoadingItemDataError()

    fun showGetSellerInfoError()

    fun showUnavailableStock()

    fun setAvailableStock(availableQuantity: Int)

    fun setSellerWithoutReputation()

    fun showStockQuantityBottomSheetSelector(maxQuantity: Int)

    fun showErrorForQuantitySelector()

    fun setProductInfoDetails(attributes: List<AttributesItems>)

    fun hideProductInfoDetails()

    fun showMoreProductInfoMessage()
}