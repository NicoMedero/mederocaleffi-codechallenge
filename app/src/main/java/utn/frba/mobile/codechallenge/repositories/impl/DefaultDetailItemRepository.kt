package utn.frba.mobile.codechallenge.repositories.impl

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.Seller
import utn.frba.mobile.codechallenge.repositories.DetailItemRepository
import utn.frba.mobile.codechallenge.services.DetailItemService

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