package mederocaleffi.nicolas.mobile.codechallenge.factory

import mederocaleffi.nicolas.mobile.codechallenge.models.AttributesItems
import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import kotlin.random.Random

class DetailItemFactory {

    fun detailItemEmpty() : DetailItem {
        return DetailItem(
            "",
            "",
            0F,
            "",
            0,
            0,
            ArrayList(),
            ArrayList()
        )
    }

    fun detailItemWithId(id: String) : DetailItem {
        return DetailItem(
            id,
            "",
            0F,
            "",
            0,
            0,
            ArrayList(),
            ArrayList()
        )
    }

    fun detailItemWithQuantityAndAttr() : DetailItem {
        return DetailItem(
            "1234",
            "",
            0F,
            "",
            0,
            100,
            ArrayList(),
            attributes = listOf(
                AttributesItems(Random.toString(), Random.toString(), Random.toString()),
                AttributesItems(Random.toString(), Random.toString(), Random.toString())
            )
        )
    }
}