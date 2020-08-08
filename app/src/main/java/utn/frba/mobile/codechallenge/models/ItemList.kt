package utn.frba.mobile.codechallenge.models

import java.io.Serializable

data class ItemList (
    val id: String,
    val title: String,
    val seller: Seller,
    val price: Float,
    val thumbnail: String,
    var like: Boolean = false
) : Serializable {
    constructor(
        id: String,
        like: Boolean
    ) : this (id, "", Seller(), 0F, "", like)
}