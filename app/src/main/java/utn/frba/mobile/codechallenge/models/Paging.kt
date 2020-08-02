package utn.frba.mobile.codechallenge.models

data class Paging (
    val total: Int,
    val offset: Int,
    val limit: Int,
    val primaryResults: Int
)