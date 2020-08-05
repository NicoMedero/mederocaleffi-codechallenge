package utn.frba.mobile.codechallenge.views.sharedCustomViews.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.views.sharedCustomViews.CustomToolbarInterface

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var view: CustomToolbarInterface

    init {
        View.inflate(context, R.layout.custom_toolbar, this)
    }

    fun initWithSearchView(view: CustomToolbarInterface) {
        this.view = view
        vSearchViewCustomToolbar.visibility = View.VISIBLE
        vSearchViewCustomToolbar.setOnQueryTextListener(setQueryListener())
    }

    fun getQueryFromSearchView(): String {
        return if (vSearchViewCustomToolbar.query.isNullOrEmpty()) {
            ""
        } else {
            vSearchViewCustomToolbar.query.toString()
        }
    }

    fun initWithLikeAndSearchButton(view: CustomToolbarInterface) {
        this.view = view

        vCustomToolbar.inflateMenu(R.menu.custom_toolbar_menu)
        vCustomToolbar.title = resources.getString(R.string.custom_toolbar_title)

        val likeButton = vCustomToolbar.menu.findItem(R.id.vLikeButtonDetailFragment)
        likeButton.setOnMenuItemClickListener {
            if (it.isChecked) {
                it.setIcon(R.drawable.ic_not_like_black)
                view.unlikeItem()
            } else {
                it.setIcon(R.drawable.ic_like_black)
                view.likeItem()
            }
            it.isChecked = !it.isChecked
            true
        }

        val searchButton = vCustomToolbar.menu
            .findItem(R.id.vSearchButtonDetailFragment)
            .actionView as SearchView

        searchButton.setOnQueryTextListener(setQueryListener())
    }

    private fun setQueryListener(): SearchView.OnQueryTextListener{
        return object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                clearFocus()
                view.queryTextSubmitted(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
    }

    fun setLikeStatus() {
        val likeButton = vCustomToolbar.menu.findItem(R.id.vLikeButtonDetailFragment)
        likeButton.apply {
            this.isChecked = true
            this.setIcon(R.drawable.ic_like_black)
        }
    }
}