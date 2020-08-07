package utn.frba.mobile.codechallenge.views.sharedCustomViews.impl

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.button_more_details.view.*
import utn.frba.mobile.codechallenge.R

class CustomButtonMoreInfo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    var onButtonClicked: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.button_more_details, this)

        /**
         * With this method, we can manage from the outside
         * the clickListener behavior.
         */
        setOnClickListener {
            onButtonClicked?.let { it() }
        }
    }

    fun setText(text: String) {
        vTextButtonMoreInfo.text = text
    }
}