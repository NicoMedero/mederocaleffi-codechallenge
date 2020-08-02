package utn.frba.mobile.codechallenge.views.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun showProgressBar() {
        vProgressBarSearchFragment.visibility = View.VISIBLE
    }

    override fun stopProgressBar() {
        vProgressBarSearchFragment.visibility = View.GONE
    }

    override fun loadQueryResults(results: List<ItemList>) {
        itemList.clear()
        itemList.addAll(results)
        itemListAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}