package utn.frba.mobile.codechallenge.models

data class DetailItem(
    val id: String,
    val title: String,
    val price: Float,
    val condition: String,
    val soldQuantity: Int,
    val availableQuantity: Int,
    val pictures: List<ItemImages>
)