package mederocaleffi.nicolas.mobile.codechallenge.factory

import mederocaleffi.nicolas.mobile.codechallenge.models.Paging
import mederocaleffi.nicolas.mobile.codechallenge.models.SearchModel

class SearchModelFactory {

    fun emptySearchModel(): SearchModel {
        return SearchModel(
            "",
            Paging(),
            emptyList()
        )
    }

    fun searchWithData(): SearchModel {
        return SearchModel(
            "",
            Paging(50, 0, 50, 50),
            listOf(
                ItemListFactory().itemListEmpty(),
                ItemListFactory().itemListEmpty()
            )
        )
    }
}