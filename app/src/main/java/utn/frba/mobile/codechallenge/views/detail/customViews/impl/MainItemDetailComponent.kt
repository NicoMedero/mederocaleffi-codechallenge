package utn.frba.mobile.codechallenge.views.detail.customViews.impl

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
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
        vShareButtonMainItemDetail.setOnClickListener{ detailView.showShareBottomSheet() }
    }

    fun setItemData(detailItem: DetailItem) {
        detailItem.run {
            vConditionMainItemDetail.text = getItemCondition(this.condition)
            vItemTitleMainItemDetail.text = this.title
            vItemPriceMainItemDetail.text = getItemPrice(this.price)
            vItemSoldQuantityMainItemDetail.text = getSoldQuantity(this.soldQuantity)
            vCarouselMainItemDetail.setImageListener(setCarouselImageListener(this.pictures))
            vCarouselMainItemDetail.addOnPageChangeListener(setOnChangeListener(this.pictures.size))
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

    private fun setOnChangeListener(picturesListSize: Int): ViewPager.OnPageChangeListener {
        return object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //Do nothing
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                vImageCountMainItemDetail.text = String.format(IMAGE_COUNT, position+1, picturesListSize)
            }

            override fun onPageSelected(position: Int) {
                //Do nothing
            }

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

    companion object {
        private const val IMAGE_COUNT = "%s / %d"
    }
}