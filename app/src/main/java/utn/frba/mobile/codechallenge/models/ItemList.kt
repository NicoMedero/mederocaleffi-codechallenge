package utn.frba.mobile.codechallenge.models

data class ItemList (
    val title: String,
    val price: Float,
    val thumbnail: String,
    var like: Boolean = false
)