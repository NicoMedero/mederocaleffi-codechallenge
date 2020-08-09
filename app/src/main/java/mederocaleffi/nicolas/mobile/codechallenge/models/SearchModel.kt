package mederocaleffi.nicolas.mobile.codechallenge.models

data class SearchModel(
    var query: String,
    val paging: Paging,
    val results: List<ItemList>
)