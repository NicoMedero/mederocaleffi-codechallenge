package mederocaleffi.nicolas.mobile.codechallenge.repositories.impl

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import mederocaleffi.nicolas.mobile.codechallenge.models.Seller
import mederocaleffi.nicolas.mobile.codechallenge.repositories.DetailItemRepository
import mederocaleffi.nicolas.mobile.codechallenge.services.DetailItemService
import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemPresenterInterface

/**
 * This repository implements the necessary calls
 * to get detail information from an Item selected and
 * the seller of that item.
 */
class DefaultDetailItemRepository : BaseRepository(), DetailItemRepository{

    override fun searchItemById(id: String, presenter: DetailItemPresenterInterface) {
        val service = retrofit.create(DetailItemService::class.java)
        val call = service.getItemsById(id)

        call.enqueue(object: Callback<DetailItem>{
            override fun onResponse(call: Call<DetailItem>, response: Response<DetailItem>) {
                if (response.body() != null && response.code() == OK_HTTP){
                    presenter.onSuccess(response.body() as DetailItem)
                } else {
                    presenter.onFailure()
                }
            }

            override fun onFailure(call: Call<DetailItem>, t: Throwable) {
                presenter.onFailure()
            }
        })
    }

    override fun searchSellerById(id: Int, presenter: DetailItemPresenterInterface) {
        val service = retrofit.create(DetailItemService::class.java)
        val call = service.getSellerById(id)

        call.enqueue(object: Callback<Seller> {
            override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                if (response.body() != null && response.code() == OK_HTTP){
                    presenter.onSuccess(response.body() as Seller)
                } else {
                    presenter.onFailureGettingSellerData()
                }
            }

            override fun onFailure(call: Call<Seller>, t: Throwable) {
                presenter.onFailureGettingSellerData()
            }
        })
    }
}