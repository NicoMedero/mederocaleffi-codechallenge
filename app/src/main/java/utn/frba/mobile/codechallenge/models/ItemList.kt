package utn.frba.mobile.codechallenge.models

import java.io.Serializable

data class ItemList (
    val id: String,
    val title: String,
    val price: Float,
    val thumbnail: String,
    var like: Boolean = false
) : Serializable