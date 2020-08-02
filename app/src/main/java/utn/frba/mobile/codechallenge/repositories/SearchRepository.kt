package utn.frba.mobile.codechallenge.repositories

import utn.frba.mobile.codechallenge.models.SearchModel

interface SearchRepository {

    fun searchByName(
        query: String,
        offset: Int?,
        limit: Int?,
        onSuccess: (SearchModel) -> Unit?,
        onFailure: () -> Unit?)
}