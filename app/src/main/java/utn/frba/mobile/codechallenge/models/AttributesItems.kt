package utn.frba.mobile.codechallenge.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttributesItems(
    val id: String,
    val name: String,
    @SerializedName("value_name")
    val valueName: String
): Serializable