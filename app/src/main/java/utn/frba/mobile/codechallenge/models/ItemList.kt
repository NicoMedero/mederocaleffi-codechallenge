package utn.frba.mobile.codechallenge.models

import android.net.Uri

data class ItemList (
    val title: String,
    val price: Float,
    val thumbnail: String,
    var like: Boolean = false
)