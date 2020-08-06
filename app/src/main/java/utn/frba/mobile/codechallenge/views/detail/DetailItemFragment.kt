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
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.models.DetailItem
import utn.frba.mobile.codechallenge.models.ItemList
import utn.frba.mobile.codechallenge.views.sharedCustomViews.CustomToolbarInterface
import utn.frba.mobile.codechallenge.views.detail.DetailItemActivity.Companion.ITEM_LIST_DATA

class DetailItemFragment : Fragment(), DetailItemView, CustomToolbarInterface {

    private lateinit var presenter: DetailItemPresenter

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
        vMainItemDetailFragment.bindWithView(this)
        vAddToLikesButtonContainerDetailFragment.apply {
            setOnClickListener {
                presenter.onLikeButtonClicked(vLikeButtonWithShareDetailFragment.isChecked)
            }
        }
        vShareButtonContainerDetailFragment.setOnClickListener {
            presenter.onShareButtonClicked()
        }
        vTextForLikeButonWithShareDetailFragment.text = getString(R.string.detail_fragment_unliked_item_text)
        vTextForShareButonWithLikeDetailFragment.text = getString(R.string.detail_fragment_share_button_text)

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

    override fun getIdIfItemWasLiked(): Int? {
        //TODO: presenter.getId()
        return null
    }

    override fun setLikeStatus() {
        vToolbarDetailFragment.setLikeStatus()
        vLikeButtonWithShareDetailFragment.isChecked = true
        vTextForLikeButonWithShareDetailFragment.text = getString(R.string.detail_fragment_liked_item_text)
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
            text = String.format(getString(R.string.detail_fragment_seller_name), nickname)
        }
    }

    override fun setSellerReputationAndQuantitySold(reputation: String, quantitySold: Int) {
        vSellerReputationAndQuantity.apply {
            visibility = View.VISIBLE
            text = String.format(getString(R.string.detail_fragment_seller_reputation_and_sold_quantity), reputation, quantitySold)
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

    companion object {
        fun newInstance(itemList: ItemList?): DetailItemFragment {
            val detailItemFragment = DetailItemFragment()
            val args = Bundle(1)
            args.putSerializable(ITEM_LIST_DATA, itemList)
            detailItemFragment.arguments = args
            return detailItemFragment
        }
        private const val SHARE_INTENT_TYPE = "text/plain"
    }
}