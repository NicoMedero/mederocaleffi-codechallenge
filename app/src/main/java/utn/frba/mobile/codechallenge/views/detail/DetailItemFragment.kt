package utn.frba.mobile.codechallenge.views.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.detail_fragment.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.views.customViews.CustomToolbarInterface

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

    companion object {
        fun newInstance() = DetailItemFragment()
    }
}