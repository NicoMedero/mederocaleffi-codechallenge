package utn.frba.mobile.codechallenge.models

import java.io.Serializable

data class Transactions(
    val completed: Int
) : Serializable {
    constructor() : this(0)
}
