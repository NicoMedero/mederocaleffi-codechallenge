package mederocaleffi.nicolas.mobile.codechallenge.factory

import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList

class ItemListFactory {

    fun itemListEmpty() : ItemList{
        return ItemList(
            "",
            false
        )
    }

    fun itemListWithId(id: String) : ItemList {
        return ItemList(
            id,
            false
        )
    }

    fun itemListWithIdAndLike(id: String) : ItemList {
        return ItemList(
            id,
            true
        )
    }
}