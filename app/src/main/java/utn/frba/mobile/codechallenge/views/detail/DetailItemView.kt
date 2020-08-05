package utn.frba.mobile.codechallenge.views.detail

import utn.frba.mobile.codechallenge.models.DetailItem

interface DetailItemView {
    fun getIdIfItemWasLiked(): Int?

    fun setLikeStatus()

    fun stopProgressBar()

    fun setMainItemDetails(detailItem: DetailItem)
}