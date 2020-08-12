package mederocaleffi.nicolas.mobile.codechallenge.factory

import mederocaleffi.nicolas.mobile.codechallenge.models.Seller
import mederocaleffi.nicolas.mobile.codechallenge.models.SellerReputation
import mederocaleffi.nicolas.mobile.codechallenge.models.Transactions
import kotlin.random.Random

class SellerFactory {

    fun sellerEmptyData() : Seller {
        return Seller()
    }

    fun sellerWithData() : Seller {
        return Seller(
            1234,
            Random.toString(),
            SellerReputation(
                "status",
                Transactions(
                    100
                )
            )
        )
    }
}