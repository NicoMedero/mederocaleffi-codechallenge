package utn.frba.mobile.codechallenge.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailItem(
    val id: String,
    val title: String,
    val price: Float,
    val condition: String,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    val pictures: List<ItemImages>
): Serializable