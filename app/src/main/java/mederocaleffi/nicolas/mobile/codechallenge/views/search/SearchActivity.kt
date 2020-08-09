package mederocaleffi.nicolas.mobile.codechallenge.views.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mederocaleffi.nicolas.mobile.codechallenge.R
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList
import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemActivity.Companion.DETAIL_ITEM_ACTIVITY_CODE
import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemActivity.Companion.ITEM_LIST_DATA
import mederocaleffi.nicolas.mobile.codechallenge.views.detail.DetailItemFragment.Companion.QUERY_FROM_DETAIL_VIEW

class SearchActivity : AppCompatActivity() {

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        if (savedInstanceState == null) {
            val searchFragment = SearchFragment.newInstance()
            searchView = searchFragment
            supportFragmentManager
                .beginTransaction()
                .add(searchFragment, SEARCH_FRAGMENT_TAG)
                .replace(R.id.vBaseActivityContainer, searchFragment)
                .commit()
        } else {
            searchView = supportFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG) as SearchFragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == DETAIL_ITEM_ACTIVITY_CODE) {
            val bundle = data?.extras
            bundle?.run {
                val query = getString(QUERY_FROM_DETAIL_VIEW)
                val item: ItemList? = getSerializable(ITEM_LIST_DATA) as ItemList?
                searchView?.updateViewFromExtras(query, item)
            }
        }
    }

    companion object {
        private const val SEARCH_FRAGMENT_TAG = "search_fragment"
    }
}