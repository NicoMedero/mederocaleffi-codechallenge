package utn.frba.mobile.codechallenge.views.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import utn.frba.mobile.codechallenge.R

class DetailItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vBaseActivityContainer, DetailItemFragment.newInstance())
                .commitNow()
        }
    }
}