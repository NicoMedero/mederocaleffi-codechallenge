package utn.frba.mobile.codechallenge.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SellerReputation(
    @SerializedName("power_seller_status")
    val sellerStatus: String,
    val transactions: Transactions
) : Serializable {
    constructor(): this("", Transactions())
}
