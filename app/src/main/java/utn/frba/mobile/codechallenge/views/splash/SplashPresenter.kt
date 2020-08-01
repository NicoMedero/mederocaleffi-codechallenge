package utn.frba.mobile.codechallenge.views.splash

import android.os.Handler

class SplashPresenter(private val view: SplashView) {

    private val splashTime = 2000L

    fun onViewCreated() {
        /*
            In this method we can make a call to the API
            to load the searchView with some data.
         */
        val handler = Handler()
        handler.postDelayed(
            { view.onSplashEnded() },
            splashTime
        )
    }
}