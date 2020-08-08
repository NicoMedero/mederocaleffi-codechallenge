package utn.frba.mobile.codechallenge.views.detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.like_and_share_detail_fragment.*
import kotlinx.android.synthetic.main.stock_quantity_selector_detail_fragment.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.models.AttributesItems
import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemList
import utn.frba.mobile.codechallenge.models.Seller
import utn.frba.mobile.codechallenge.views.sharedCustomViews.CustomToolbarInterface
import utn.frba.mobile.codechallenge.views.detail.DetailItemActivity.Companion.ITEM_LIST_DATA
import utn.frba.mobile.codechallenge.views.detail.customViews.impl.StockSelector

class DetailItemFragment : Fragment(), DetailItemView, CustomToolbarInterface {

    private lateinit var presenter: DetailItemPresenter
    private var stockSelector: StockSelector? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = DetailItemPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.detail_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vToolbarDetailFragment.initWithLikeAndSearchButton(this)
        vMainItemDetailFragment.setPresenter(presenter)
        vProductInfoContainer.apply {
            setPresenter(presenter)
            setButtonText(getString(R.string.detail_fragment_see_more_product_info_button_text))
        }
        vAddToLikesButtonContainerDetailFragment.apply {
            setOnClickListener {
                presenter.onLikeButtonClicked(vLikeButtonWithShareDetailFragment.isChecked)
            }
        }
        vLikeButtonWithShareDetailFragment.apply {
            setOnClickListener {
                presenter.onLikeButtonClicked(this.isChecked)
            }
        }
        vShareButtonContainerDetailFragment.setOnClickListener {
            presenter.onShareButtonClicked()
        }
        vStockQuantitySelectorDetailFragment.setOnClickListener {
            presenter.onStockSelectorClicked()
        }
        vTextForLikeButonWithShareDetailFragment.text = getString(R.string.detail_fragment_unliked_item_text)
        vTextForShareButonWithLikeDetailFragment.text = getString(R.string.detail_fragment_share_button_text)

        if (savedInstanceState != null) {
            presenter.restoreDetailItemState(savedInstanceState.getSerializable(DETAIL_ITEM_KEY) as DetailItem)
            presenter.restoreLikedState(savedInstanceState.getBoolean(LIKE_STATE_KEY))
            presenter.restoreSellerDetailState(savedInstanceState.getSerializable(SELLER_DETAIL_KEY) as Seller)
        }

        presenter.setItemData(arguments?.getSerializable(ITEM_LIST_DATA) as ItemList)
    }

    override fun unlikeItem() {
        vLikeButtonWithShareDetailFragment.isChecked = false
        vTextForLikeButonWithShareDetailFragment.text = getString(R.string.detail_fragment_unliked_item_text)
    }

    override fun likeItem() {
        vLikeButtonWithShareDetailFragment.isChecked = true
        vTextForLikeButonWithShareDetailFragment.text = getString(R.string.detail_fragment_liked_item_text)
    }

    override fun queryTextSubmitted(query: String) {
        Toast.makeText(context, "Query: $query", Toast.LENGTH_SHORT).show()
    }

    override fun getItemForResult(): ItemList? {
        return presenter.getItemState(vLikeButtonWithShareDetailFragment.isChecked)
    }

    override fun setLikeStatus() {
        vToolbarDetailFragment.setLikeStatus()
        vLikeButtonWithShareDetailFragment.isChecked = true
        vTextForLikeButonWithShareDetailFragment.text = getString(R.string.detail_fragment_liked_item_text)
    }

    override fun setNotLikeStatus() {
        unlikeItem()
        vToolbarDetailFragment.setNotLikeStatus()
    }

    override fun stopProgressBar() {
        vProgressBarAndErrorContainerDetailItemFragment.visibility = View.GONE
    }

    override fun setMainItemDetails(detailItem: DetailItem) {
        vMainItemDetailFragment.setItemData(detailItem)
    }

    override fun onUnlikedItem() {
        unlikeItem()
        vToolbarDetailFragment.changeLikeButtonState()
    }

    override fun onLikedItem() {
        likeItem()
        vToolbarDetailFragment.changeLikeButtonState()
    }

    override fun showShareBottomSheet() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = SHARE_INTENT_TYPE
        val shareBody = getString(R.string.detail_fragment_share_body)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        requireContext().startActivity(Intent.createChooser(sharingIntent, getString(R.string.detail_fragment_share_title)))
    }

    override fun setSellerName(nickname: String) {
        vSellerNameDetailFragment.apply {
            visibility = View.VISIBLE
            text = getString(R.string.detail_fragment_seller_name, nickname)
        }
    }

    override fun setSellerReputationAndQuantitySold(reputation: String, quantitySold: Int) {
        vSellerReputationAndQuantity.apply {
            visibility = View.VISIBLE
            text = getString(R.string.detail_fragment_seller_reputation_and_sold_quantity, reputation, quantitySold)
        }
    }

    override fun setSellerWithoutReputation() {
        vSellerReputationAndQuantity.apply {
            visibility = View.VISIBLE
            text = getString(R.string.detail_fragment_seller_without_reputation_yet)
        }
    }

    override fun showLoadingItemDataError() {
        vProgressBarDetailItemFragment.visibility = View.GONE
        vToolbarDetailFragment.hideLikeButton()
        vErrorImageDetailItemFragment.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                background = requireContext().getDrawable(R.drawable.sad_emoji)
                visibility = View.VISIBLE
            }
        }
        Toast.makeText(requireContext(), getString(R.string.detail_fragment_error_getting_item_data), Toast.LENGTH_LONG).show()
    }

    override fun showGetSellerInfoError() {
        vSellerNameDetailFragment.apply {
            visibility = View.VISIBLE
            text = getString(R.string.detail_fragment_error_getting_seller_data)
        }
    }

    override fun showUnavailableStock() {
        vStockQuantitySelectorTitle.text = getString(R.string.detail_fragment_stock_unavailable)
        vStockQuantitySelectedDetailFragment.text = getText(R.string.empty_text)
        vStockAvailableQuantityDetailFragment.text = getString(R.string.detail_fragment_unavailable_stock_detailed)
        vStockQuantitySelectedDetailFragment.setOnClickListener {  }
    }

    override fun setAvailableStock(availableQuantity: Int) {
        vStockQuantitySelectorTitle.text = getString(R.string.detail_fragment_stock_available)
        vStockQuantitySelectedDetailFragment.text = "1"
        vStockAvailableQuantityDetailFragment.text = getString(R.string.detail_fragment_stock_available_quantity, availableQuantity.toString())
    }

    override fun showStockQuantityBottomSheetSelector(maxQuantity: Int) {
        stockSelector = StockSelector(maxQuantity)
        stockSelector?.show(fragmentManager!!, stockSelector?.tag)
    }

    override fun showErrorForQuantitySelector() {
        Toast.makeText(requireContext(), getString(R.string.detail_fragment_error_opening_stock_selector), Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(DETAIL_ITEM_KEY, presenter.getDetailItemInstance())
        outState.putSerializable(SELLER_DETAIL_KEY, presenter.getSellerDetailInstance())
        outState.putBoolean(LIKE_STATE_KEY, vLikeButtonWithShareDetailFragment.isChecked)
    }

    override fun setProductInfoDetails(attributes: List<AttributesItems>) {
        vProductInfoContainer.loadAttributes(attributes)
    }

    override fun hideProductInfoDetails() {
        vProductInfoContainer.visibility = View.GONE
    }

    override fun showMoreProductInfoMessage() {
        Toast.makeText(requireContext(), getString(R.string.detail_fragment_more_product_details_message), Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        /**
         * This is to prevent the app crash
         * when rotating the device with
         * the bottom sheet instantiated.
         */
        stockSelector?.dismiss()
    }

    companion object {
        fun newInstance(itemList: ItemList?): DetailItemFragment {
            val detailItemFragment = DetailItemFragment()
            val args = Bundle(1)
            args.putSerializable(ITEM_LIST_DATA, itemList)
            detailItemFragment.arguments = args
            return detailItemFragment
        }
        private const val SHARE_INTENT_TYPE = "text/plain"
        private const val DETAIL_ITEM_KEY = "detail_item"
        private const val SELLER_DETAIL_KEY = "seller_detail"
        private const val LIKE_STATE_KEY = "like_state"
        const val QUERY_FROM_DETAIL_VIEW = "query_from_detail_view"
    }
}