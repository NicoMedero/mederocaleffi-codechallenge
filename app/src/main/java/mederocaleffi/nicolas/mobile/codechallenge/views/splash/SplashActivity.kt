package mederocaleffi.nicolas.mobile.codechallenge.views.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mederocaleffi.nicolas.mobile.codechallenge.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.vBaseActivityContainer, SplashFragment.newInstance())
                .commitNow()
        }
    }
}