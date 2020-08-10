package mederocaleffi.nicolas.mobile.codechallenge.views.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mederocaleffi.nicolas.mobile.codechallenge.R
import mederocaleffi.nicolas.mobile.codechallenge.models.ItemList

class DetailItemActivity : AppCompatActivity() {

    private var detailItemView: DetailItemView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        if (savedInstanceState == null) {
            val detailItemFragment = DetailItemFragment.newInstance(intent.getSerializableExtra(ITEM_LIST_DATA) as ItemList)
            detailItemView = detailItemFragment
            supportFragmentManager
                .beginTransaction()
                .add(detailItemFragment, DETAIL_FRAGMENT_TAG)
                .replace(R.id.vBaseActivityContainer, detailItemFragment)
                .commitNow()
        } else {
            detailItemView = supportFragmentManager.findFragmentByTag(DETAIL_FRAGMENT_TAG) as DetailItemView
        }
    }

    /**
     * This is used to get the ID and the like state of the item,
     * and send it to SearchFragment to update the view if the item was liked.
     */
    override fun onBackPressed() {
        val itemList = detailItemView?.getItemForResult()
        val returnIntent = Intent()
        if (itemList == null) {
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
        } else {
            returnIntent.putExtra(ITEM_LIST_DATA, itemList)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    companion object {
        private const val DETAIL_FRAGMENT_TAG = "detail_fragment"
        const val ITEM_LIST_DATA = "item_list_data"
        const val DETAIL_ITEM_ACTIVITY_CODE = 9000
    }
}