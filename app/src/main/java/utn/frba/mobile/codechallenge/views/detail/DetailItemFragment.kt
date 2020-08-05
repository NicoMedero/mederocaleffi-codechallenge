package utn.frba.mobile.codechallenge.views.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.detail_fragment.*
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


        presenter.setItemData(arguments?.getSerializable(ITEM_LIST_DATA) as ItemList)
    }

    override fun unlikeItem() {
        Toast.makeText(context, "Unlike item", Toast.LENGTH_SHORT).show()
    }

    override fun likeItem() {
        Toast.makeText(context, "Like item", Toast.LENGTH_SHORT).show()
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
    }

    override fun stopProgressBar() {
        vProgressBarDetailItemFragment.visibility = View.GONE
    }

    override fun setMainItemDetails(detailItem: DetailItem) {
        vMainItemDetailFragment.setItemData(detailItem)
    }

    companion object {
        fun newInstance(itemList: ItemList?): DetailItemFragment {
            val detailItemFragment = DetailItemFragment()
            val args = Bundle(1)
            args.putSerializable(ITEM_LIST_DATA, itemList)
            detailItemFragment.arguments = args
            return detailItemFragment
        }
    }
}