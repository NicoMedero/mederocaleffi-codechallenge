package utn.frba.mobile.codechallenge.views.detail

import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.Seller
import utn.frba.mobile.codechallenge.models.SellerReputation

interface DetailItemView {
    fun getIdIfItemWasLiked(): Int?

    fun setLikeStatus()

    fun stopProgressBar()

    fun setMainItemDetails(detailItem: DetailItem)

    fun onUnlikedItem()

    fun onLikedItem()

    fun showShareBottomSheet()

    fun setSellerName(nickname: String)

    fun setSellerReputationAndQuantitySold(reputation: String, quantitySold: Int)

}