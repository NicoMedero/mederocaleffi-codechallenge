package mederocaleffi.nicolas.mobile.codechallenge.repositories

import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import mederocaleffi.nicolas.mobile.codechallenge.models.Seller

interface DetailItemRepository {

    fun searchItemById(
        id: String,
        onSuccess: (DetailItem) -> Unit?,
        onFailure: () -> Unit
    )

    fun searchSellerById(
        id: Int,
        onSuccess: (Seller) -> Unit?,
        onFailure: () -> Unit
    )
}