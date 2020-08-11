package mederocaleffi.nicolas.mobile.codechallenge.repositories

import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemPresenterInterface

interface DetailItemRepository {

    fun searchItemById(
        id: String,
        presenter: DetailItemPresenterInterface
    )

    fun searchSellerById(
        id: Int,
        presenter: DetailItemPresenterInterface
    )
}