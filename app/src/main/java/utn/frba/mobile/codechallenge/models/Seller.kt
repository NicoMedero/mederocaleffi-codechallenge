package utn.frba.mobile.codechallenge.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Seller(
    val id: Int,
    val nickname: String,
    @SerializedName("seller_reputation")
    val reputation: SellerReputation
) : Serializable {
    constructor() : this(0, "", SellerReputation())
}