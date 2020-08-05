package utn.frba.mobile.codechallenge.views.customViews.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.main_item_detail_fragment.view.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemImages
import utn.frba.mobile.codechallenge.views.detail.DetailItemView

class MainItemDetailComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    private lateinit var detailView: DetailItemView

    init {
        View.inflate(context, R.layout.main_item_detail_fragment, this)
    }

    fun bindWithView(view: DetailItemView) {
        detailView = view
    }

    fun setItemData(detailItem: DetailItem) {
        detailItem.run {
            vConditionMainItemDetail.text = getItemCondition(this.condition)
            vItemTitleMainItemDetail.text = this.title
            vItemPriceMainItemDetail.text = getItemPrice(this.price)
            vItemSoldQuantityMainItemDetail.text = getSoldQuantity(this.soldQuantity)
            vCarouselMainItemDetail.setImageListener(setCarouselImageListener(this.pictures))
            vCarouselMainItemDetail.pageCount = detailItem.pictures.size
        }

    }

    private fun setCarouselImageListener(picturesList: List<ItemImages>): ImageListener {
        return ImageListener { position, imageView ->
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            Picasso.get()
                .load(picturesList[position].secureUrl)
                .placeholder(R.drawable.item_image_placeholder)
                .error(R.drawable.item_image_placeholder)
                .into(imageView)
        }
    }

    private fun getItemCondition(condition: String): String {
        return if (condition == "new") {
            "Nuevo | "
        } else {
            "Usado | "
        }
    }

    private fun getItemPrice(price: Float): String {
        return "$ $price"
    }

    private fun getSoldQuantity(sold: Int): String {
        return if (sold != 1) {
            "$sold vendidos"
        } else {
            "$sold vendido"
        }
    }
}