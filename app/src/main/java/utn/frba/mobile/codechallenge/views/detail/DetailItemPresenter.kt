package utn.frba.mobile.codechallenge.views.detail

import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemList
import utn.frba.mobile.codechallenge.models.Seller
import utn.frba.mobile.codechallenge.repositories.DetailItemRepository
import utn.frba.mobile.codechallenge.repositories.impl.DefaultDetailItemRepository
import kotlin.math.max

class DetailItemPresenter(private val view: DetailItemView) {

    private val repository: DetailItemRepository = DefaultDetailItemRepository()
    private var detailItem: DetailItem? = null

    fun setItemData(itemData: ItemList?) {
        if (itemData == null) {
            view.showLoadingItemDataError()
            return
        }

        itemData.run {
            setLikeStatus(this.like)
            repository.searchItemById(this.id, { onSuccess(it) }, { onFailure() })
            repository.searchSellerById(this.seller.id, { onSuccess(it) }, { onFailureGettingSellerData() })
        }
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
    }

    private fun onSuccess(seller: Seller) {
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
        view.showStockQuantityBottomSheetSelector()
    }

}