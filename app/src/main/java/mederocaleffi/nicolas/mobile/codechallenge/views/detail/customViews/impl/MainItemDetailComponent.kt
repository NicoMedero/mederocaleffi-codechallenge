package mederocaleffi.nicolas.mobile.codechallenge.views.detail.customViews.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.main_item_detail_fragment.view.*
import mederocaleffi.nicolas.mobile.codechallenge.R
import mederocaleffi.nicolas.mobile.codechallenge.models.DetailItem
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemImages
import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemPresenter

class MainItemDetailComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    private lateinit var presenter: DetailItemPresenter

    init {
        View.inflate(context, R.layout.main_item_detail_fragment, this)
    }

    fun setPresenter(presenter: DetailItemPresenter) {
        this.presenter = presenter
        vShareButtonMainItemDetail.setOnClickListener{ presenter.onShareButtonClicked() }
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
            vImageCountMainItemDetail.text = context.getString(R.string.detail_fragment_image_count_chip, (vCarouselMainItemDetail.currentItem+1).toString(), this.pictures.size)
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
                //Do nothing
            }

            override fun onPageSelected(position: Int) {
                vImageCountMainItemDetail.text = context.getString(R.string.detail_fragment_image_count_chip, (position+1).toString(), picturesListSize)
            }

        }
    }

    private fun getItemCondition(condition: String): String {
        return if (condition == STATE_NEW) {
            context.getString(R.string.detail_fragment_new_state)
        } else {
            context.getString(R.string.detail_fragment_used_state)
        }
    }

    private fun getItemPrice(price: Float): String {
        return context.getString(R.string.detail_fragment_price, price.toString())
    }

    private fun getSoldQuantity(sold: Int): String {
        return if (sold != 1) {
            context.getString(R.string.detail_fragment_plural_sold, sold.toString())
        } else {
            context.getString(R.string.detail_fragment_one_sold, sold.toString())
        }
    }

    companion object {
        private const val STATE_NEW = "new"
    }
}