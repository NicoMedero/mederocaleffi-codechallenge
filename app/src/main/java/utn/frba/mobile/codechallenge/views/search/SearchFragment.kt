package utn.frba.mobile.codechallenge.views.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_fragment.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.models.ItemList
import utn.frba.mobile.codechallenge.views.sharedCustomViews.CustomToolbarInterface

class SearchFragment : Fragment(), SearchView, CustomToolbarInterface {

    private lateinit var presenter: SearchPresenter
    private var itemList = ArrayList<ItemList>()
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var itemListLayoutManager: LinearLayoutManager


    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = SearchPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.search_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vToolbarSearchFragment.initWithSearchView(this)

        itemListAdapter = ItemListAdapter(itemList)
        itemListLayoutManager = LinearLayoutManager(requireContext())

        vRecyclerViewSearchFragment.apply {
            layoutManager = itemListLayoutManager
            adapter = itemListAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val totalItems = layoutManager?.itemCount
                    val lastItem = itemListLayoutManager.findLastVisibleItemPosition()

                    if (lastItem + 1 == totalItems) {
                        presenter.getMoreItems(totalItems, vToolbarSearchFragment.getQueryFromSearchView())
                    }
                }
            })
        }

        if (savedInstanceState != null) {
            val savedItemList: List<ItemList> = savedInstanceState.getSerializable(ITEMS_LIST_KEY) as List<ItemList>
            itemList.addAll(savedItemList)
            itemListAdapter.notifyDataSetChanged()
        }
    }

    override fun queryTextSubmitted(query: String) {
        presenter.onQuerySubmit(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(ITEMS_LIST_KEY, itemList)
    }

    override fun clearData() {
        itemList.clear()
        itemListAdapter.notifyDataSetChanged()
    }

    override fun showProgressBar() {
        vProgressBarSearchFragment.visibility = View.VISIBLE
        vFailureImageSearchFragment.visibility = View.GONE
        vEmptyResultsImageSearchFragment.visibility = View.GONE
    }

    override fun stopProgressBar() {
        vProgressBarSearchFragment.visibility = View.GONE
    }

    override fun loadQueryResults(results: List<ItemList>) {
        itemList.clear()
        itemList.addAll(results)
        itemListAdapter.notifyDataSetChanged()
    }

    override fun addItemsAtTheEnd(results: List<ItemList>) {
        itemList.addAll(itemList.lastIndex, results)
        itemListAdapter.notifyDataSetChanged()
    }

    override fun onFailureQuery() {
        vFailureImageSearchFragment.visibility = View.VISIBLE
        Toast.makeText(requireContext(), getString(R.string.search_view_failure_query_message), Toast.LENGTH_LONG).show()
    }

    override fun emptyResultsFromQuery() {
        vEmptyResultsImageSearchFragment.visibility = View.VISIBLE
        Toast.makeText(requireContext(), getString(R.string.search_view_empty_results_message), Toast.LENGTH_LONG).show()
    }

    override fun onFailureGettingMoreItems() {
        Toast.makeText(requireContext(), getString(R.string.search_view_failure_get_more_items_message), Toast.LENGTH_LONG).show()
    }

    override fun noMoreItemsToShow() {
        Toast.makeText(requireContext(), getString(R.string.search_view_no_more_items_to_show_message), Toast.LENGTH_SHORT).show()
    }

    override fun unlikeItem() {
        //Do nothing
    }

    override fun likeItem() {
        //Do nothing
    }

    companion object {
        fun newInstance() = SearchFragment()
        private const val ITEMS_LIST_KEY = "item_list"
    }
}