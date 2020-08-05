package utn.frba.mobile.codechallenge.models

import com.google.gson.annotations.SerializedName

data class ItemImages(
    @SerializedName("secure_url")
    val secureUrl: String,
    val size: String
)
