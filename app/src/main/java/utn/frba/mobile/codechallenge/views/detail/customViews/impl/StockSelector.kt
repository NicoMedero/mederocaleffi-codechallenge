package utn.frba.mobile.codechallenge.views.detail.customViews.impl

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_stock_quantity_layout.view.*
import kotlinx.android.synthetic.main.stock_selector_text_view.view.*
import utn.frba.mobile.codechallenge.R

class StockSelector(
    private val maxQuantity: Int
) : BottomSheetDialogFragment() {

    private lateinit var stockSelectorView: View

    override fun setupDialog(dialog: Dialog, style: Int) {
        stockSelectorView = View.inflate(requireContext(), R.layout.bottom_sheet_stock_quantity_layout, null)
        dialog.setContentView(stockSelectorView)
        loadItemsIntoTheView(stockSelectorView)
    }

    private fun loadItemsIntoTheView(view: View) {
        var iterator = 1
        val limit = if (maxQuantity > 6) {
            6
        } else {
            maxQuantity
        }

        while (iterator <= limit) {
            /**
             * All this TextViews must be a custom
             * to hold their position and know the value
             * when the user clicks one.
             */
            val textView = TextView.inflate(requireContext(), R.layout.stock_selector_text_view, null)
            textView.vInsideItem.text = setItemText(iterator)
            textView.setOnClickListener {
                Toast.makeText(requireContext(), it.vInsideItem.text, Toast.LENGTH_SHORT).show()
            }
            textView.background = setSelectedBackground(iterator)

            view.vBottomSheetStockQuantityContainer.addView(textView)

            iterator++
        }
    }

    private fun setItemText(itemPosition: Int): String {
        return when(itemPosition) {
            1 -> "1 unidad"
            6 -> "Más de 6 unidades"
            else -> "$itemPosition unidades"
        }
    }

    private fun setSelectedBackground(itemPosition: Int): Drawable? {
        return if (itemPosition == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireContext().getDrawable(R.color.light_gray)
            } else {
                null
            }
        } else {
            null
        }
    }
}