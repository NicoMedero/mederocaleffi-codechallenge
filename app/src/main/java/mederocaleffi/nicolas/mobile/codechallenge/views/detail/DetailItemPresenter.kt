package mederocaleffi.nicolas.mobile.codechallenge.views.detail

import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.models.Seller
import mederocaleffi.nicolas.mobile.codechallenge.repositories.DetailItemRepository
import mederocaleffi.nicolas.mobile.codechallenge.repositories.impl.DefaultDetailItemRepository
import java.io.Serializable

class DetailItemPresenter(private val view: DetailItemView) {

    private val repository: DetailItemRepository = DefaultDetailItemRepository()
    private var detailItem: DetailItem? = null
    private var sellerDetails: Seller? = null

    fun setItemData(itemData: ItemList?) {
        if (itemData == null) {
            view.showLoadingItemDataError()
            return
        }

        if (detailItem == null) {
            itemData.run {
                setLikeStatus(this.like)
                repository.searchItemById(this.id, { onSuccess(it) }, { onFailure() })
            }
        } else {
            restoreDetailItemFromRotate(detailItem!!)
        }

        if (sellerDetails == null) {
            repository.searchSellerById(itemData.seller.id, { onSuccess(it) }, { onFailureGettingSellerData() })
        } else {
            restoreSellerDetailsFromRotate(sellerDetails!!)
        }
    }

    private fun restoreDetailItemFromRotate(detailItem: DetailItem) {
        onSuccess(detailItem)
    }

    private fun restoreSellerDetailsFromRotate(sellerDetails: Seller) {
        onSuccess(sellerDetails)
    }

    private fun setLikeStatus(likeStatus: Boolean) {
        if (likeStatus) {
            view.setLikeStatus()
        }
    }

    private fun onSuccess(detailItem: DetailItem) {
        this.detailItem = detailItem
        view.stopProgressBar()
        view.setMainItemDetails(detailItem)

        if (detailItem.availableQuantity == 0) {
            view.showUnavailableStock()
        } else {
            view.setAvailableStock(detailItem.availableQuantity)
        }

        if (detailItem.attributes.isNotEmpty()) {
            view.setProductInfoDetails(detailItem.attributes.filter { item -> attributesIdList.contains(item.id) })
        } else {
            view.hideProductInfoDetails()
        }
    }

    private fun onSuccess(seller: Seller) {
        sellerDetails = seller
        if (seller.nickname.isNotEmpty()) {
            view.setSellerName(seller.nickname)
        }
        if (!seller.reputation.sellerStatus.isNullOrEmpty()) {
            view.setSellerReputationAndQuantitySold(seller.reputation.sellerStatus, seller.reputation.transactions.completed)
        } else {
            view.setSellerWithoutReputation()
        }
    }

    private fun onFailure() {
        view.showLoadingItemDataError()
    }

    private fun onFailureGettingSellerData() {
        view.showGetSellerInfoError()
    }

    fun onLikeButtonClicked(checked: Boolean) {
        if (checked) {
            view.onUnlikedItem()
        } else {
            view.onLikedItem()
        }
    }

    fun onShareButtonClicked() {
        view.showShareBottomSheet()
    }

    fun onStockSelectorClicked() {
        if (detailItem?.availableQuantity != null) {
            view.showStockQuantityBottomSheetSelector(detailItem!!.availableQuantity)
        } else {
            view.showErrorForQuantitySelector()
        }
    }

    fun getDetailItemInstance(): Serializable? {
        return detailItem
    }

    fun getSellerDetailInstance(): Serializable? {
        return sellerDetails
    }

    fun restoreDetailItemState(detailItem: DetailItem?) {
        this.detailItem = detailItem
    }

    fun restoreSellerDetailState(seller: Seller?) {
        sellerDetails = seller
    }

    fun restoreLikedState(likedState: Boolean) {
        if (likedState) {
            view.setLikeStatus()
        } else {
            view.setNotLikeStatus()
        }
    }

    fun onMoreProductInfoButtonClicked() {
        view.showMoreProductInfoMessage()
    }

    fun getItemState(isLiked: Boolean): ItemList? {
        return if (detailItem != null) {
            ItemList(
                detailItem!!.id,
                isLiked
            )
        } else {
            null
        }
    }

    companion object {
        private val attributesIdList: List<String> = listOf(
            "BRAND", "MODEL", "WEIGHT", "ITEM_CONDITION", "SELLER_SKU",
            "BATTERY_CAPACITY", "CARRIER","CPU_MODELS", "DISPLAY_SIZE", "INTERNAL_MEMORY"
        )
    }
}