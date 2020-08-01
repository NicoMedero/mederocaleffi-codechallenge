package utn.frba.mobile.codechallenge.views.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.search_fragment.*
import utn.frba.mobile.codechallenge.R

class SearchFragment : Fragment(), SearchView {

    private lateinit var presenter: SearchPresenter

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

        vSearchViewSearchFragment.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vSearchViewSearchFragment.clearFocus()
                presenter.onQuerySubmit(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun showProgressBar() {

    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}