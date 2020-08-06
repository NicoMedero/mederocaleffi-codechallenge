package utn.frba.mobile.codechallenge.repositories

import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.Seller

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