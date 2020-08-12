package mederocaleffi.nicolas.mobile.codechallenge.repositories

import mederocaleffi.nicolas.mobile.codechallenge.views.search.SearchPresenterInterface

interface SearchRepository {

    fun searchByName(
        query: String,
        offset: Int?,
        presenter: SearchPresenterInterface
    )
}