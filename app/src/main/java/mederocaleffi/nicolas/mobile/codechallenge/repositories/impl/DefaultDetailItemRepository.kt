package mederocaleffi.nicolas.mobile.codechallenge.repositories.impl

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import mederocaleffi.nicolas.mobile.codechallenge.models.Seller
import mederocaleffi.nicolas.mobile.codechallenge.repositories.DetailItemRepository
import mederocaleffi.nicolas.mobile.codechallenge.services.DetailItemService

/**
 * This repository implements the necessary calls
 * to get detail information from an Item selected and
 * the seller of that item.
 */
class DefaultDetailItemRepository : BaseRepository(), DetailItemRepository{

    override fun searchItemById(
        id: String,
        onSuccess: (DetailItem) -> Unit?,
        onFailure: () -> Unit
    ) {
        val service = retrofit.create(DetailItemService::class.java)
        val call = service.getItemsById(id)

        call.enqueue(object: Callback<DetailItem>{
            override fun onResponse(call: Call<DetailItem>, response: Response<DetailItem>) {
                if (response.body() != null && response.code() == OK_HTTP){
                    onSuccess.invoke(response.body() as DetailItem)
                } else {
                    onFailure.invoke()
                }
            }

            override fun onFailure(call: Call<DetailItem>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }

    override fun searchSellerById(id: Int, onSuccess: (Seller) -> Unit?, onFailure: () -> Unit) {
        val service = retrofit.create(DetailItemService::class.java)
        val call = service.getSellerById(id)

        call.enqueue(object: Callback<Seller> {
            override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                if (response.body() != null && response.code() == OK_HTTP){
                    onSuccess.invoke(response.body() as Seller)
                }
            }

            override fun onFailure(call: Call<Seller>, t: Throwable) {
                onFailure.invoke()
            }
        })
    }
}