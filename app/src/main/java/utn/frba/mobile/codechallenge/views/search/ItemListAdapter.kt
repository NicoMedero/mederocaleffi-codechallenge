package utn.frba.mobile.codechallenge.views.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_layout.view.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.models.ItemList

class ItemListAdapter(private val results: List<ItemList>) : RecyclerView.Adapter<ItemListAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_list_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.apply {
            setItemData(results[position])
            //onItemClicked(results[position])
        }
    }

    class ItemHolder(private val viewHolder: View): RecyclerView.ViewHolder(viewHolder) {

        fun setItemData(itemList: ItemList) {
            viewHolder.run {
                Picasso.get()
                    .load(itemList.thumbnail)
                    .placeholder(R.drawable.item_image_placeholder)
                    .error(R.drawable.item_image_placeholder)
                    .into(vItemListImage)
                vItemListDescription.text = itemList.title
                vItemListPrice.text = getPrice(itemList.price)
                vItemListLikeButton.apply {
                    this.isChecked = itemList.like
                    setOnClickListener { itemList.like = this.isChecked }
                }
            }
        }

        fun onItemClicked(itemList: ItemList){
            //TODO: to be implemented
        }

        private fun getPrice(price: Float): String {
            return "$ $price"
        }
    }
}