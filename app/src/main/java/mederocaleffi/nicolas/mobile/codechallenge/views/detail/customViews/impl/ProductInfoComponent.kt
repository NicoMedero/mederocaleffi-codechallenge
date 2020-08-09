package mederocaleffi.nicolas.mobile.codechallenge.views.detail.customViews.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.product_info_detail_fragment.view.*
import mederocaleffi.nicolas.mobile.codechallenge.R
import mederocaleffi.nicolas.mobile.codechallenge.models.AttributesItems
import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemPresenter

class ProductInfoComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    private var presenter: DetailItemPresenter? = null
    private val linearLayoutManager = LinearLayoutManager(context)
    private lateinit var productInfoAdapter: ProductInfoItemAdapter

    init {
        View.inflate(context, R.layout.product_info_detail_fragment, this)
    }

    fun setPresenter(presenter: DetailItemPresenter) {
        this.presenter = presenter
        vButtonMoreProductInfo.onButtonClicked = { presenter.onMoreProductInfoButtonClicked() }
    }

    fun loadAttributes(attributesList: List<AttributesItems>) {
        productInfoAdapter = ProductInfoItemAdapter(attributesList)
        vRecyclerViewProductInfo.apply {
            layoutManager = linearLayoutManager
            adapter = productInfoAdapter
        }
    }

    fun setButtonText(text: String) {
        vButtonMoreProductInfo.setText(text)
    }
}