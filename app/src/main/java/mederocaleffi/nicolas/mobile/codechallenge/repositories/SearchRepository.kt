package mederocaleffi.nicolas.mobile.codechallenge.repositories

import mederocaleffi.nicolas.mobile.codechallenge.models.SearchModel

interface SearchRepository {

    fun searchByName(
        query: String,
        offset: Int?,
        onSuccess: (SearchModel) -> Unit?,
        onFailure: () -> Unit?)
}