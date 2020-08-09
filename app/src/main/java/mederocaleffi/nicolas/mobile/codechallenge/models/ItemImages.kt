package mederocaleffi.nicolas.mobile.codechallenge.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemImages(
    @SerializedName("secure_url")
    val secureUrl: String,
    val size: String
): Serializable
