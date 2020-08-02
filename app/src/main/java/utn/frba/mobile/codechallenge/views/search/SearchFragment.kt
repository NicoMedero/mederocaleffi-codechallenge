package utn.frba.mobile.codechallenge.views.search

import android.content.Context
import android.os.Build
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

class SearchFragment : Fragment(), SearchView {

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

        itemListAdapter = ItemListAdapter(itemList)
        itemListLayoutManager = LinearLayoutManager(requireContext())

        vRecyclerViewSearchFragment.apply {
            this.layoutManager = itemListLayoutManager
            adapter = itemListAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val totalItems = layoutManager?.itemCount
                    val lastItem = itemListLayoutManager.findLastVisibleItemPosition()

                    if (lastItem + 1 == totalItems) {
                        presenter.getMoreItems(totalItems)
                    }
                }
            })
        }

        vSearchViewSearchFragment.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vSearchViewSearchFragment.clearFocus()
                if (!query.isNullOrEmpty()){
                    presenter.onQuerySubmit(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun clearData() {
        itemList.clear()
        itemListAdapter.notifyDataSetChanged()
    }

    override fun showProgressBar() {
        vProgressBarSearchFragment.visibility = View.VISIBLE
        vFailureImageSearchFragment.visibility = View.GONE
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vFailureImageSearchFragment.setImageDrawable(requireContext().getDrawable(R.drawable.sad_emoji))
        }
        Toast.makeText(requireContext(), getString(R.string.search_view_failure_query_message), Toast.LENGTH_LONG).show()
    }

    override fun emptyResultsFromQuery() {
        vFailureImageSearchFragment.visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vFailureImageSearchFragment.setImageDrawable(requireContext().getDrawable(R.drawable.thinking_emoji))
        }
        Toast.makeText(requireContext(), getString(R.string.search_view_empty_results_message), Toast.LENGTH_LONG).show()
    }

    override fun onFailureGettingMoreItems() {
        Toast.makeText(requireContext(), getString(R.string.search_view_failure_get_more_items_message), Toast.LENGTH_LONG).show()
    }

    override fun noMoreItemsToShow() {
        Toast.makeText(requireContext(), getString(R.string.search_view_no_more_items_to_show_message), Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}