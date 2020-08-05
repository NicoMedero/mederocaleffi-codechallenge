package utn.frba.mobile.codechallenge.repositories

import utn.frba.mobile.codechallenge.models.DetailItem

interface DetailItemRepository {

    fun searchItemById(
        id: String,
        onSuccess: (DetailItem) -> Unit?,
        onFailure: () -> Unit
    )
}