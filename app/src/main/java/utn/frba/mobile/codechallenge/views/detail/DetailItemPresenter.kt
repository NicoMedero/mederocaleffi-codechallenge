package utn.frba.mobile.codechallenge.views.detail

import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemList
import utn.frba.mobile.codechallenge.models.Seller
import utn.frba.mobile.codechallenge.repositories.DetailItemRepository
import utn.frba.mobile.codechallenge.repositories.impl.DefaultDetailItemRepository

class DetailItemPresenter(private val view: DetailItemView) {

    private val repository: DetailItemRepository = DefaultDetailItemRepository()

    fun setItemData(itemData: ItemList?) {
        if (itemData == null) {
            view.stopProgressBar()
            //TODO: Manage error.
            return
        }

        itemData.run {
            setLikeStatus(this.like)
            repository.searchItemById(this.id, { onSuccess(it) }, { onFailure() })
            repository.searchSellerById(this.seller.id, { onSuccess(it) }, { onFailure(it) })
        }
    }

    private fun setLikeStatus(likeStatus: Boolean) {
        if (likeStatus) {
            view.setLikeStatus()
        }
    }

    private fun onSuccess(detailItem: DetailItem) {
        view.stopProgressBar()
        view.setMainItemDetails(detailItem)
    }

    private fun onSuccess(seller: Seller) {
        if (seller.nickname.isNotEmpty()) {
            view.setSellerName(seller.nickname)
        }
        if (seller.reputation.sellerStatus.isNotEmpty()) {
            view.setSellerReputationAndQuantitySold(seller.reputation.sellerStatus, seller.reputation.transactions.completed)
        }
    }

    private fun onFailure() {
        view.stopProgressBar()
        //TODO: Implement
    }

    private fun onFailure(message: String?) {
        //TODO: Implement
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
}