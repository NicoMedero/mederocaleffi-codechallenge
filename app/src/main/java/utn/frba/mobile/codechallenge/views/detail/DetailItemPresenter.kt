package utn.frba.mobile.codechallenge.views.detail

import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemList
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
        }
    }

    private fun setLikeStatus(likeStatus: Boolean) {
        if (likeStatus) {
            view.setLikeStatus()
        }
    }

    private fun onSuccess(detailItem: DetailItem) {
        view.stopProgressBar()
        detailItem.run {

        }
    }

    private fun onFailure() {
        view.stopProgressBar()
        //TODO: Implement
    }
}