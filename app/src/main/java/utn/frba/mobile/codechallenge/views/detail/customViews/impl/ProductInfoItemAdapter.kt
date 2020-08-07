package utn.frba.mobile.codechallenge.views.detail.customViews.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_info_item_detail_fragment.view.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.models.AttributesItems

class ProductInfoItemAdapter(private val attributesList: List<AttributesItems>) : RecyclerView.Adapter<ProductInfoItemAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_info_item_detail_fragment, parent, false)
        )
    }

    override fun getItemCount(): Int = attributesList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.apply {
            setItemNameAndValueName(attributesList[position].name, attributesList[position].valueName, position)
        }
    }

    class ItemHolder(private val itemHolder: View) : RecyclerView.ViewHolder(itemHolder){

        fun setItemNameAndValueName(name: String, valueName: String, position: Int) {
            itemHolder.apply {
                if (isEven(position)) {
                    vNameBackgroundDarkProductInfoItem.visibility = View.VISIBLE
                    vValueNameBackgroundDarkProductInfoItem.visibility = View.VISIBLE
                } else {
                    vNameBackgroundLightProductInfoItem.visibility = View.VISIBLE
                    vValueNameBackgroundLightProductInfoItem.visibility = View.VISIBLE
                }
                vNameProductInfoItem.text = name
                vValueNameProductInfoItem.text = valueName
            }
        }

        private fun isEven(number: Int): Boolean {
            return number % 2 == 0
        }
    }
}